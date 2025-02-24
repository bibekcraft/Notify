package com.example.notes

import java.security.Timestamp

data class Note(
    var id: String = "",
    val title: String = "",
    val content: String = ""
) {
    val timestamp: Timestamp?
        get() {
            TODO()
        }
}
