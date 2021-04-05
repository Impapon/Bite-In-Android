package com.example.androidformclasstwokotlinbootcamp

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.service.autofill.UserData
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.time.Year
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var uri: Uri? = null
    private  var userdate: String?=null
    private var usertime: String?=null
    private var userBlood:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pickimageid.setOnClickListener(this)
        pickdate.setOnClickListener(this)
        picktime.setOnClickListener(this)
        submitid.setOnClickListener(this)

        val bloodlist  = resources.getStringArray(R.array.blood_group)
        bloodgroupsnipperid.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bloodlist)
        bloodgroupsnipperid.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                userBlood =bloodlist.get(position)
                pickbloodtextid.text=bloodlist.get(position)
            }
        }
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.pickimageid -> {
                openDialog()
            }
            R.id.pickdate->{
               takeDate()
            }
            R.id.picktime->{
                takeTime()
            }
            R.id.submitid->{

            val userName = picknameid.text.toString()
                val userEmail = pickemailid.text.toString()
                var userGender = ""
                var userSkills = ""
                when(radioGroup.checkedRadioButtonId){
                    R.id.male->{
                        userGender =male.text.toString()
                    }
                    R.id.female->{
                        userGender =female.text.toString()
                    }
                }
                if(bangla.isChecked){
                    userSkills += "Bangla "
                }
                if(englis.isChecked){
                    userSkills += "English "
                }
                if(arabic.isChecked){
                    userSkills += "Arabic "
                }
                if(hindi.isChecked){
                    userSkills += "Hindi "
                }

                val student = Student(uri,userName,userEmail,userGender,
                    userdate,usertime,userBlood,userSkills)
                val intent = Intent(this,ProfileActivity::class.java)
                intent.putExtra("student",student)
                startActivity(intent)


            }
        }
    }

    private fun takeTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute=calendar.get(Calendar.MINUTE)
        val timePickerDialog:TimePickerDialog = TimePickerDialog(this,
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            usertime =""+hourOfDay+" : "+minute
            timetextid.text = ""+hourOfDay+" : "+minute
        },hour,minute,true)
        timePickerDialog.show()
    }

    private fun takeDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month= calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog: DatePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{
            view,year,monthOfYear,dayOfMonth ->
            userdate = ""+dayOfMonth+"/"+monthOfYear+"/"+year
            datetextid.text= ""+dayOfMonth+"/"+monthOfYear+"/"+year
        },year,month+1,day)
        datePickerDialog.show()
    }

    private fun openDialog() {
        val dialog = AlertDialog.Builder(this)
        val option = arrayOf("Gallery", "Camera")
        dialog.setTitle("Choose a option")
        dialog.setItems(option) { _, which ->
            val selected = option[which]

            if (selected == "Gallery") {
                openGallety()
            } else {
                openCamera()
            }

        }
        val alert = dialog.create().show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 2)
    }

    private fun openGallety() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            uri = data?.data
            pickimageid.setImageURI(data?.data)
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data")as Bitmap

            uri = getImageFromBitmap(this,bitmap)
            pickimageid.setImageBitmap(bitmap)
        }


    }

    private fun getImageFromBitmap(context: Context, bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        val compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return  Uri.parse((path.toString()))
    }
}