package com.example.sqlitebiteinkotlin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Spalish : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("database",Context.MODE_PRIVATE)
        if(sharedPreferences.contains("status")){
            val value = sharedPreferences.getString("status","notLogin")
            if(value == "login"){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            else if(value=="notLogin"){
                startActivity(Intent(this,Login::class.java))
                finish()
            }

        }else{
            startActivity(Intent(this,Login::class.java))
            finish()
        }

    }
}