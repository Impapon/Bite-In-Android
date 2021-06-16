package com.example.roomdatabasemultipletable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys= ["id","dept"])
data class StudentDepartment (
    val id : String,
    val dept:String
)