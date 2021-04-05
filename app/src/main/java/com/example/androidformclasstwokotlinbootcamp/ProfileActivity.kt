package com.example.androidformclasstwokotlinbootcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val student:Student? = intent.getParcelableExtra("student")
        if(student!=null){
            profileimageid.setImageURI(student.uri)
            pnameid.text = student.name
            emailText.text = student.email
            genderText.text = student.gender
            dateText.text  = student.date
            timeText.text  =student.time
            bloodText.text = student.blood
            skillsText.text = student.skill


        }
    }
}