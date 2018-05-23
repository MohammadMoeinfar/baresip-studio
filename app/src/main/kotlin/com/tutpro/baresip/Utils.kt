package com.tutpro.baresip

import android.content.Context
import android.support.v7.app.AlertDialog
import android.util.Log

import java.io.*

object Utils {

    fun getFileContents(file: File): String {
        if (!file.exists()) {
            Log.e("Baresip", "Failed to find file: " + file.path)
            return ""
        } else {
            Log.e("Baresip", "Found file: " + file.path)
            val length = file.length().toInt()
            val bytes = ByteArray(length)
            try {
                val `in` = FileInputStream(file)
                try {
                    `in`.read(bytes)
                } finally {
                    `in`.close()
                }
                return String(bytes)
            } catch (e: java.io.IOException) {
                Log.e("Baresip", "Failed to read file: " + file.path + ": " +
                        e.toString())
                return ""
            }

        }
    }

    fun putFileContents(file: File, contents: String) {
        try {
            val fOut = FileOutputStream(file.absoluteFile, false)
            val fWriter = OutputStreamWriter(fOut)
            try {
                fWriter.write(contents)
                fWriter.close()
                fOut.close()
            } catch (e: java.io.IOException) {
                Log.e("Baresip", "Failed to put contents to file: " + e.toString())
            }

        } catch (e: java.io.FileNotFoundException) {
            Log.e("Baresip", "Failed to find contents file: " + e.toString())
        }

    }

    fun alertView(context: Context, title: String, message: String) {
        // val alertDialog = AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert).create()
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }

    fun uriHostPart(uri: String): String {
        return uri.substringAfter("@")
                .substringBefore(":")
                .substringBefore(";")
                .substringBefore(">")
    }

    fun uriUserPart(uri: String): String {
        return uri.substringAfter(":").substringBefore("@")
    }

    fun checkUserID(id: String): Boolean {
        return Regex("^[a-zA-Z]([._-]|[a-zA-Z0-9]){1,49}\$").matches(id)
    }

    fun checkTelNo(no: String): Boolean {
        return Regex("^[+]?[0-9]{1,16}\$").matches(no)
    }

    fun checkIP(ip: String): Boolean {
        return Regex("^(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))/$").matches(ip)
    }

    fun checkDomain(domain: String): Boolean {
        val parts = domain.split(".")
        for (p in parts) {
            if (p.length == 0 || p.endsWith("-") || !Regex("^[a-zA-z]([-]|[a-zA-Z0-9])+\$").matches(p))
                return false
        }
        return true
    }

    fun checkPrintASCII(s: String): Boolean {
        if (s == "") return true
        val printASCIIRegex = Regex("^[ -~]*\$")
        return printASCIIRegex.matches(s)
    }

    fun checkUint(s: String): Boolean {
        val uintRegex = Regex("^[0-9]+\$")
        return uintRegex.matches(s)
    }

    fun implode(list: List<String>, sep: String): String {
        var res = ""
        for (s in list) {
            if (res == "")
                res = s
            else
                res = res + sep + s
        }
        return res
    }

    external fun cmd_exec(cmd: String): Int
    external fun uri_decode(uri: String): Boolean
    external fun audio_codecs(): String

}