package com.example.roomdatabasemultipletable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Department (
    @PrimaryKey(autoGenerate = false)
    val dept:String
)