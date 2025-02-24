// NoteDetailsActivity.kt
package com.example.notes

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class NoteDetailsActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveNoteBtn: ImageButton
    private lateinit var pageTitleTextView: TextView
    private lateinit var deleteNoteTextViewBtn: TextView
    private var docId: String? = null
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)

        titleEditText = findViewById(R.id.notes_title_text)
        contentEditText = findViewById(R.id.notes_content_text)
        saveNoteBtn = findViewById(R.id.save_note_btn)
        pageTitleTextView = findViewById(R.id.page_title)
        deleteNoteTextViewBtn = findViewById(R.id.deleteNoteTextViewBtn)

        titleEditText.setText(intent.getStringExtra("title"))
        contentEditText.setText(intent.getStringExtra("content"))
        docId = intent.getStringExtra("docId")

        isEditMode = !docId.isNullOrEmpty()
        if (isEditMode) {
            pageTitleTextView.text = "Edit your note"
            deleteNoteTextViewBtn.visibility = View.VISIBLE
        }

        saveNoteBtn.setOnClickListener { saveNote() }
        deleteNoteTextViewBtn.setOnClickListener { deleteNoteFromFirebase() }
    }

    private fun saveNote() {
        val noteTitle = titleEditText.text.toString()
        val noteContent = contentEditText.text.toString()

        if (noteTitle.isEmpty()) {
            titleEditText.error = "Title is required"
            return
        }

        val note = Note(noteTitle, noteContent, Timestamp.now().toString())
        saveNoteToFirebase(note)
    }

    private fun saveNoteToFirebase(note: Note) {
        val documentReference: DocumentReference = if (isEditMode) {
            Utility.collectionReferenceForNotes.document(docId!!)
        } else {
            Utility.collectionReferenceForNotes.document()
        }

        documentReference.set(note)
            .addOnSuccessListener {
                Utility.showToast(this, "Note saved successfully")
                finish()
            }
            .addOnFailureListener {
                Utility.showToast(this, "Failed to save note")
            }
    }

    private fun deleteNoteFromFirebase() {
        Utility.collectionReferenceForNotes.document(docId!!)
            .delete()
            .addOnSuccessListener {
                Utility.showToast(this, "Note deleted successfully")
                finish()
            }
            .addOnFailureListener {
                Utility.showToast(this, "Failed to delete note")
            }
    }
}