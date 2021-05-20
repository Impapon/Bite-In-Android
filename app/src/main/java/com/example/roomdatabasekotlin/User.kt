package com.example.roomdatabasekotlin

import android.view.autofill.AutofillId
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val name:String,
    val email:String
) {
    @PrimaryKey(autoGenerate = true)
    val id:Int? =null
}
