package com.tutpro.baresip

class CallRow(val aor: String, val peerURI: String, val direction: Int, val time: String, val index: Int) {

    val directions = ArrayList<Int>()
    val indexes = ArrayList<Int>()

    init {
        directions.add(direction)
        indexes.add(index)
    }
}
