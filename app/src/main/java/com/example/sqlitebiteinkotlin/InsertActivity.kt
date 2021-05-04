package com.example.sqlitebiteinkotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitebiteinkotlin.databinding.ActivityInsertBinding
import com.example.sqlitebiteinkotlin.databinding.ActivityInsertBinding.bind
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

      binding.listviewid.setOnClickListener {
          startActivity(Intent(this,MainActivity::class.java))
      }

        binding.callid.setOnClickListener {
            val number = binding.phnnumberid.text.toString().trim()
            if(number.isNotEmpty()){
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                startActivity(intent)

            }
            else{
                Snackbar.make(binding.insertlayoutid,"Please insert Number",Snackbar.LENGTH_LONG).show()
            }
        }
        binding.youtubeid.setOnClickListener {
            startActivity(Intent(this,YoutubeActivity::class.java))
        }
    }
}