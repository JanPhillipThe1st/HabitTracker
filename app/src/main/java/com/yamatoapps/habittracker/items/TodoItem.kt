package com.yamatoapps.habittracker.items

data class TodoItem(val documentID:String,val taskName:String, val taskFrequency: String, val taskStartTime:String, val taskEndTime:String,
                    var isDone:Boolean)
