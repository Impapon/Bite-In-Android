package com.example.sqlitebiteinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitebiteinkotlin.databinding.ActivityInsertBinding
import com.example.sqlitebiteinkotlin.databinding.ActivityInsertBinding.inflate
import com.google.android.material.snackbar.Snackbar

class InsertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInsertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.saveid.setOnClickListener{
            val name = binding.nameid.text.toString()
            val email = binding.emailid.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty()){
                val value  = db.insert(name,email)
                if(value==(-1).toLong()){
                    Snackbar.make(binding.insertlayoutid,"Insert Failed",Snackbar.LENGTH_LONG).show()
                }
                else{
                    Snackbar.make(binding.insertlayoutid,"Insert Successfull",Snackbar.LENGTH_LONG).show()
                }
            }
            else{
                Snackbar.make(binding.insertlayoutid,"Please insert Data",Snackbar.LENGTH_LONG).show()
            }
        }
    }
}