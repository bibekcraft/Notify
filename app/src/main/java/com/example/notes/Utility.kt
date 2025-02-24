package com.example.notes

import com.google.firebase.firestore.FirebaseFirestore
import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object Utility {
    val collectionReferenceForNotes = FirebaseFirestore.getInstance().collection("notes")

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun timestampToString(timestamp: Timestamp?): String {
        return timestamp?.toDate()?.let {
            SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault()).format(it)
        } ?: "No date"
    }
}