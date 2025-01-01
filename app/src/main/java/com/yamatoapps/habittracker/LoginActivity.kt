package com.yamatoapps.habittracker

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvUsername:TextView = findViewById(R.id.tvUsername);
        val tvPassword:TextView = findViewById(R.id.tvPassword);
        val btnLogin:Button = findViewById(R.id.btnLogin);
        val tvCreateAccount:TextView = findViewById(R.id.tvCreateAccount);
        val progressDialog:ProgressDialog = ProgressDialog(this)

        btnLogin.setOnClickListener {
            progressDialog.setTitle("Logging in...")
            progressDialog.show()
            db.collection("Users").where(
                Filter.and(
                    Filter.equalTo("username", tvUsername.text.toString()),
                    Filter.equalTo("password", tvPassword.text.toString())
                ),
            ).get().addOnSuccessListener {
                val alertDialog= MaterialAlertDialogBuilder(this)
                if (!it.documents.isEmpty()){
                    //Dismiss progress dialog
                    progressDialog.dismiss()
                    //create an AlertDialog object
                    val userID = it.documents.get(0).id
                    val homePageIntent : Intent = Intent(this,HomePage::class.java)
                    homePageIntent.putExtra("userID",userID)
                    alertDialog.setTitle("Login Success!").setMessage("Successfully logged in!").setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                        startActivity(homePageIntent)
                    }.show()
                    //Navigate to homepage

                }
                else{

                    progressDialog.dismiss()
                    alertDialog.setTitle("Login Failed!").setMessage("Invalid username/password.").setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }.show()
                }

            }
        }
    }
}