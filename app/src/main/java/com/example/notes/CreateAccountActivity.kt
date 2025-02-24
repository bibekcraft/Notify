package com.example.notes

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var createAccountBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loginBtnTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text)
        createAccountBtn = findViewById(R.id.create_account_btn)
        progressBar = findViewById(R.id.progress_bar)
        loginBtnTextView = findViewById(R.id.login_text_view_btn)

        createAccountBtn.setOnClickListener { createAccount() }
        loginBtnTextView.setOnClickListener { finish() }
    }

    private fun createAccount() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (!validateData(email, password, confirmPassword)) return

        createAccountInFirebase(email, password)
    }

    private fun createAccountInFirebase(email: String, password: String) {
        changeInProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                changeInProgress(false)
                if (task.isSuccessful) {
                    Utility.showToast(this, "Account created. Verify your email.")
                    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()
                    FirebaseAuth.getInstance().signOut()
                    finish()
                } else {
                    Utility.showToast(this, task.exception?.localizedMessage ?: "Unknown error")
                }
            }
    }

    private fun changeInProgress(inProgress: Boolean) {
        progressBar.visibility = if (inProgress) View.VISIBLE else View.GONE
        createAccountBtn.visibility = if (inProgress) View.GONE else View.VISIBLE
    }

    private fun validateData(email: String, password: String, confirmPassword: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Invalid email"
            return false
        }
        if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            return false
        }
        return true
    }
}