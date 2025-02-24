// Utility.kt
package com.example.notes

import android.content.Context
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun showToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    val collectionReferenceForNotes: CollectionReference
        get() = FirebaseFirestore.getInstance().collection("notes")
            .document(FirebaseAuth.getInstance().currentUser?.uid
                ?: throw IllegalStateException("User not logged in"))
            .collection("my_notes")

    fun timestampToString(timestamp: Timestamp?): String {
        return timestamp?.toDate()?.let {
            SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(it)
        } ?: "N/A"
    }
}