package com.yamatoapps.habittracker.items

import android.app.AlertDialog
import android.content.DialogInterface
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.yamatoapps.habittracker.R

class TodoItemAdapter(private val todoItems: ArrayList<TodoItem>,val checkChangedListener:CompoundButton.OnCheckedChangeListener) : RecyclerView.Adapter<TodoItemAdapter.ViewHolder>() {
    val db = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemAdapter.ViewHolder, position: Int) {
        val todoItem = todoItems[position]

        // sets the text to the textview from our itemHolder class
        holder.tvTaskName.text = todoItem.taskName
        holder.tvTaskFrequency.text = todoItem.taskFrequency
        holder.tvTaskTime.text = todoItem.taskStartTime + " - " + todoItem.taskEndTime
        holder.linearLayoutCheckBoxContainer.setOnClickListener{
            if(holder.cbMarkDone.isChecked) {holder.cbMarkDone.isChecked = false} else {holder.cbMarkDone.isChecked = true}
        }
        holder.cbMarkDone.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            val alertDialog = AlertDialog.Builder(holder.itemView.context)
            alertDialog.setTitle("Deleting task")
            alertDialog.setMessage("Are you sure you want to delete this task?")
            alertDialog.setPositiveButton("YES",DialogInterface.OnClickListener { dialogInterface, i ->
                db.collection("Tasks").document(todoItem.documentID).delete().addOnSuccessListener {
                    dialogInterface.dismiss()
                    Toast.makeText(holder.itemView.context,"Item deleted",Toast.LENGTH_SHORT).show()
                }
            })
            alertDialog.setNegativeButton("NO",DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
            }).create().show()
            todoItems[position].isDone = b
            //Add the finishedTask to the arraylist
            todoItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, todoItems.size)

        }
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val tvTaskFrequency: TextView = itemView.findViewById(R.id.tvTaskFrequency)
        val tvTaskTime: TextView = itemView.findViewById(R.id.tvTaskTime)
        val linearLayoutCheckBoxContainer: LinearLayout = itemView.findViewById(R.id.linearLayoutCheckBoxContainer)
        val cbMarkDone: CheckBox = itemView.findViewById(R.id.cbMarkDone)
    }
}
