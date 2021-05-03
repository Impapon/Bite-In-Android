package com.example.sqlitebiteinkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.sqlitebiteinkotlin.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
   private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            val sharedPreferences = getSharedPreferences("database",Context.MODE_PRIVATE)
            val edit = sharedPreferences.edit()
            edit.apply {
                putString("status","login")

            }.apply()
        startActivity(Intent(this,MainActivity::class.java))
            finish()

        }

    }
}