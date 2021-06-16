package com.example.roomdatabasemultipletable

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.roomdatabasemultipletable.databinding.ActivityInsertBinding
import java.net.URI

@Suppress("DEPRECATION")
class InsertActivity : AppCompatActivity(),View.OnClickListener {
    private var uri: Uri?= null
    public lateinit var binding: ActivityInsertBinding
    public lateinit var dao: UserDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageID.setOnClickListener(this)
        binding.saveID.setOnClickListener(this)
        binding.viewID.setOnClickListener(this)

        dao = UserDB.getInstance(this).myDao
        setSpinner()

    }

    private fun setSpinner() {
        val dept= resources.getStringArray(R.array.department)
        val adapter =ArrayAdapter(this,R.layout.customsnipper,dept)
        adapter.setDropDownViewResource(R.layout.customsniperdrawable)
        binding.SpinnerID.adapter = adapter
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageID->{
                openGallery()
            }
            R.id.saveID->{

                val name  = binding.NemeID.text.toString()
                val id = binding.vrstyID.text.toString()
                val value  = binding.SpinnerID.selectedItem.toString()
                if(name.isNotEmpty() && id.isNotEmpty() && uri != null){
                    val student = Student(name,id,uri.toString(),value)
                    val department = Department(value)
                    val studentDepartment = StudentDepartment(id,value)
                    val res1 = dao.insertStudent(student)
                    val res2  =dao.insertDepartment(department)
                    val res3 = dao.insertStudentDepartment(studentDepartment)
                    if(res1==-(1).toLong() && res2==(-1).toLong() && res3==(-1).toLong()){
                        Toast.makeText(this,"insertion Failed",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"insertion success",Toast.LENGTH_LONG).show()
                    }

                }
                else{
                    Toast.makeText(this,"Insert Data First",Toast.LENGTH_LONG).show()
                }
            }

            R.id.viewID->{

                startActivity(Intent(this,MainActivity::class.java))
            }

        }
    }

    private fun openGallery() {
        val intent= Intent(Intent.ACTION_PICK)
        intent.type= "image/*"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode== RESULT_OK){
            uri= data?.data
            binding.imageID.setImageURI(uri)
        }
    }
}