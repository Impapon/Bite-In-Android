package com.example.roomdatabasemultipletable

import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
val name:String,

@PrimaryKey(autoGenerate = false)
val id:String,
val image:String,
val dept: String
)