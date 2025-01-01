package com.yamatoapps.habittracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.yamatoapps.habittracker.items.TodoItem
import com.yamatoapps.habittracker.items.TodoItemAdapter
import com.yamatoapps.habittracker.task_management.AddNewTask
import de.hdodenhof.circleimageview.CircleImageView

class HomePage : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val rvTodoItems:RecyclerView = findViewById(R.id.rvTodoItems)
        val btnAddItem:Button= findViewById(R.id.btnAddItem)
        val tvUserWelcomeText:TextView= findViewById(R.id.tvUserWelcomeText)
        val civProfileImage= findViewById<CircleImageView>(R.id.civProfileImage)
        val currentUserID = intent.getStringExtra("userID")

        // this creates a vertical layout Manager
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<TodoItem>()

        db.collection("Tasks").limit(10).get().addOnSuccessListener {
            for (document in it.documents){
                data.add(TodoItem(document.id.toString(),document["task_name"].toString(),document["task_frequency"].toString(),document["task_start_time"].toString(),document["task_end_time"].toString(),false))
            }
            // This will pass the ArrayList to our Adapter
            val adapter = TodoItemAdapter(data, { compoundButton: CompoundButton, b: Boolean ->

            })
            // Setting the Adapter with the recyclerview
            rvTodoItems.adapter = adapter
        }




        btnAddItem.setOnClickListener{
            startActivityForResult(Intent(this,AddNewTask::class.java),Toast.LENGTH_SHORT)
        }

        currentUserID?.let {
            db.collection("Users").document(it).get().addOnSuccessListener {
                val userData = it.data
                if (userData != null) {
                    tvUserWelcomeText.append(userData.get("full_name").toString()+"!")
                }
            }
        }


    }
}