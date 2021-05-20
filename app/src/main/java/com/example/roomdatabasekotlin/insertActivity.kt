package com.example.roomdatabasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomdatabasekotlin.databinding.ActivityInsertBinding
import com.example.roomdatabasekotlin.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class insertActivity : AppCompatActivity() {
    private lateinit var dao: UserDAO
    lateinit var binding: ActivityInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dao= UserDB.getInstance(this).myDao

        binding.saveid.setOnClickListener {
            val name= binding.nameid.text.toString()
            val email= binding.emailid.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty()){
                val user= User(name, email)
                val res= dao.insert(user)
                if(res==(-1).toLong()){
                    Snackbar.make(binding.insertlayoutid,"Insert Failed", Snackbar.LENGTH_LONG).show()
                }
                else{
                    Snackbar.make(binding.insertlayoutid,"Insert Success", Snackbar.LENGTH_LONG).show()
                }

            }
        }
    }
}