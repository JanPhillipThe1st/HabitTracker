package com.yamatoapps.habittracker.task_management

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.yamatoapps.habittracker.R
import java.lang.Math.random
import java.util.Calendar
import kotlin.math.ceil


class AddNewTask : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val timeStart =Calendar.getInstance()
    val timeEnd =Calendar.getInstance()
    val frequency = arrayListOf<String>("Daily","Weekly")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        val tvRandomQuote = findViewById<TextView>(R.id.tvRandomQuote)
        val tvTaskName = findViewById<TextView>(R.id.tvTaskName)
        val spinnerTaskFrequency = findViewById<Spinner>(R.id.spinnerTaskFrequency)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnSelectStartTime = findViewById<Button>(R.id.btnSelectStartTime)
        val btnSelectEndTime = findViewById<Button>(R.id.btnSelectEndTime)
        var startTimeisSet=false
        var endTimeisSet= false
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, frequency)
        adapter.setDropDownViewResource(   android.R.layout
            .simple_spinner_dropdown_item
        )
        spinnerTaskFrequency.setAdapter(adapter)
        btnSelectStartTime.setOnClickListener{
            val timePickerDialog =
                TimePickerDialog(
                    this,
                    { timePicker, i, i1 ->
                        timeStart.set(Calendar.HOUR_OF_DAY, i)
                        timeStart.set(Calendar.MINUTE, i1)
                        timeStart.set(Calendar.SECOND, 0)
                        timeStart.set(Calendar.MILLISECOND, 0)
                        var ampmString = ""
                        ampmString = if (timeStart.get(Calendar.HOUR_OF_DAY) < 12) {
                            "AM"
                        } else {
                            "PM"
                        }
                        val minuteString = String.format("%02d", timeStart.get(Calendar.MINUTE))
                        if(timeEnd.get(Calendar.HOUR_OF_DAY) == 0||timeStart.get(Calendar.HOUR_OF_DAY) == 12){
                            btnSelectStartTime.setText("12:" +
                                        minuteString + " " + ampmString
                                )
                        }
                        else{
                            btnSelectStartTime.setText(
                                timeStart.get(Calendar.HOUR).toString() + ":" +
                                        minuteString + " " + ampmString
                            )
                        }
                        startTimeisSet=true
                    }, 0, 0, false
                )
            timePickerDialog.show()
        }
        btnSelectEndTime.setOnClickListener{
            val timePickerDialog =
                TimePickerDialog(
                    this,
                    { timePicker, i, i1 ->

                        timeEnd.set(Calendar.HOUR_OF_DAY, i)
                        timeEnd.set(Calendar.MINUTE, i1)
                        timeEnd.set(Calendar.SECOND, 0)
                        timeEnd.set(Calendar.MILLISECOND, 0)
                        var ampmString = ""
                       if (timeEnd.get(Calendar.HOUR_OF_DAY) < 12) {
                           ampmString =   "AM"
                        } else {
                           ampmString =   "PM"
                        }
                        val minuteString = String.format("%02d", timeEnd.get(Calendar.MINUTE))
                        if(timeEnd.get(Calendar.HOUR_OF_DAY) == 0||timeEnd.get(Calendar.HOUR_OF_DAY) == 12){
                            btnSelectEndTime.setText("12:" +
                                        minuteString + " " + ampmString
                            )
                        }
                        else{
                            btnSelectEndTime.setText(
                            timeEnd.get(Calendar.HOUR).toString() + ":" +
                                    minuteString + " " + ampmString
                            )
                        }

                        endTimeisSet=true
                    }, 0, 0, false
                )
            timePickerDialog.show()
        }



        var quotes= arrayListOf<String>(
            "“Appear weak when you are strong, and strong when you are weak.”\n― Sun Tzu, The Art of War",
            "“The supreme art of war is to subdue the enemy without fighting.”\n― Sun Tzu, The Art of War",
            "“If you know the enemy and know yourself, you need not fear the result of a hundred battles. If you know yourself but not the enemy, for every victory gained you will also suffer a defeat. If you know neither the enemy nor yourself, you will succumb in every battle.”\n― Sun Tzu, The Art of War",
            "“Let your plans be dark and impenetrable as night, and when you move, fall like a thunderbolt.”\n― Sun Tzu, The Art of War",
            "“In the midst of chaos, there is also opportunity”\n― Sun-Tzu, A Arte da Guerra",
            "“Supreme excellence consists of breaking the enemy's resistance without fighting.”\n― Sun Tzu, The Art of War",
            "“All warfare is based on deception. Hence, when we are able to attack, we must seem unable; when using our forces, we must appear inactive; when we are near, we must make the enemy believe we are far away; when far away, we must make him believe we are near.”\n― Sun tzu, The Art of War",
            "“Victorious warriors win first and then go to war, while defeated warriors go to war first and then seek to win”\n― Sun Tzu, The Art of War",
            "“The greatest victory is that which requires no battle.”\n― Sun Tzu, The Art of War",
            "“If your enemy is secure at all points, be prepared for him. If he is in superior strength, evade him. If your opponent is temperamental, seek to irritate him. Pretend to be weak, that he may grow arrogant. If he is taking his ease, give him no rest. If his forces are united, separate them. If sovereign and subject are in accord, put division between them. Attack him where he is unprepared, appear where you are not expected .”\n― Sun Tzu, The Art of War",
            "“To know your Enemy, you must become your Enemy.”\n― Sun Tzu",
            "“There is no instance of a nation benefitting from prolonged warfare.”\n― Sun Tzu, The Art of War",
            "“Engage people with what they expect; it is what they are able to discern and confirms their projections. It settles them into predictable patterns of response, occupying their minds while you wait for the extraordinary moment — that which they cannot anticipate.”\n― Sun Tzu, The Art of War",
            "“Treat your men as you would your own beloved sons. And they will follow you into the deepest valley.”\n― Sun Tzu, The Art of War",
            "“Thus we may know that there are five essentials for victory:\n1 He will win who knows when to fight and when not to fight.\n2 He will win who knows how to handle both superior and inferior forces.\n3 He will win whose army is animated by the same spirit throughout all its ranks.\n4 He will win who, prepared himself, waits to take the enemy unprepared.\n5 He will win who has military capacity and is not interfered with by the sovereign.”\n― Sun Tzu, The Art of War",
            "“Even the finest sword plunged into salt water will eventually rust.”\n― Sun Tzu",
            "“Move swift as the Wind and closely-formed as the Wood. Attack like the Fire and be still as the Mountain.”\n― Sun Tzu, The Art of War",
            "“When you surround an army, leave an outlet free. Do not press a desperate foe too hard.”\n― sun tzu, The Art of War",
            "“Opportunities multiply as they are seized.”\n― Sun Tzu",
            "“There are not more than five musical notes, yet the combinations of these five give rise to more melodies than can ever be heard.\n\nThere are not more than five primary colours, yet in combination\nthey produce more hues than can ever been seen.\n\nThere are not more than five cardinal tastes, yet combinations of\nthem yield more flavours than can ever be tasted.”\n― Sun Tzu, The Art of War",
            "“who wishes to fight must first count the cost”\n― Sun Tzu, The Art of War",
            "“When the enemy is relaxed, make them toil. When full, starve them. When settled, make them move.”\n― Sun Tzu, The Art of War",
            "“The art of war is of vital importance to the State. It is a matter of life and death, a road either to safety or to ruin. Hence it is a subject of inquiry which can on no account be neglected.”\n― Sun Tzu, The Art of War",
            "“If you wait by the river long enough, the bodies of your enemies will float by.”\n― Sun Tzu",
            "“know yourself and you will win all battles”\n― Sun Tzu",
            "“To win one hundred victories in one hundred battles is not the acme of skill. To subdue the enemy without fighting is the acme of skill.”\n― Sun Tzu, The Art of War",
            "“So in war, the way is to avoid what is strong, and strike at what is weak.”\n― Sun Tzu, The Art of War",
            "“Be extremely subtle even to the point of formlessness. Be extremely mysterious even to the point of soundlessness. Thereby you can be the director of the opponent's fate.”\n― Sun Tzu, The Art of War",
            "“The wise warrior avoids the battle.”\n― Sun Tzu, The Art of War",
            "“Build your opponent a golden bridge to retreat across.”\n― Sun Tzu"
        )
        val randomQuote = quotes[ceil(random()*quotes.size).toInt()]
        tvRandomQuote.text = randomQuote

        btnSave.setOnClickListener{
            //Ask the user for confirmation
            //Create a new alert dialog object
            var alertDialogBuilder = AlertDialog.Builder(this)
            //Set titles
            alertDialogBuilder.setMessage("Are you sure you want to add this task?").setTitle("Add new task")

            alertDialogBuilder.setPositiveButton("YES") { dialogInterface: DialogInterface, _: Int ->
                //Create a progress dialog
                val progressDialog = ProgressDialog(this)
                progressDialog.setMessage("Adding your task...")
                progressDialog.create()
                progressDialog.show()
                //Create a task Map (datatype)
                var taskMap = HashMap<String,String>()
                taskMap.put("task_name",tvTaskName.text.toString())
                taskMap.put("task_frequency",frequency[spinnerTaskFrequency.selectedItemPosition])
                taskMap.put("task_start_time",btnSelectStartTime.text.toString())
                taskMap.put("task_end_time",btnSelectEndTime.text.toString())
                //Call firebase firestore object
                db.collection("Tasks").add(taskMap).addOnSuccessListener {
                    //Upon success, display an alert dialog
                    val taskUploadSuccessAlertDialog = AlertDialog.Builder(this)
                    taskUploadSuccessAlertDialog.setMessage("Task saved successfully!").setOnDismissListener {
                        tvTaskName.text = ""
                        btnSelectStartTime.text = "Select start Time"
                        btnSelectEndTime.text = "Select end Time"
                    }.create().show()
                }
                //Close the dialog
                progressDialog.dismiss()

            }
            //Before showing the alertdialog for adding new task,
            //We need to ensure that the inputs are valid

            //Create a validation alert object
            val validationAlertDialog = AlertDialog.Builder(this)
            validationAlertDialog.setTitle("Incomplete input.")
            if (tvTaskName.text.toString().length <1){
            validationAlertDialog.setMessage("Please input a valid task name").create().show()
                return@setOnClickListener
            }
            if (btnSelectEndTime.text.toString().contains("AM",true)){
                validationAlertDialog.setMessage("Please input end time.").create().show()
                return@setOnClickListener
            }
            if (btnSelectStartTime.text.toString().contains("AM",true)){
                validationAlertDialog.setMessage("Please input end time.").create().show()
                return@setOnClickListener
            }
            alertDialogBuilder.create().show()


        }
    }
}