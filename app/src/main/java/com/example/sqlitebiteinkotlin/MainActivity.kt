package com.example.sqlitebiteinkotlin

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitebiteinkotlin.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener,MyAdapter.OnItemClick {

    lateinit var dbHelper: DBHelper
    lateinit var myAdapter: MyAdapter
    private val userList = ArrayList<User>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "User List"
        binding.insertbuttonid.setOnClickListener(this)
        binding.logoutbuttonid.setOnClickListener(this)
        binding.recyclerviewid.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewid.setHasFixedSize(true)
        loadData()

    }

    private fun loadData() {
         dbHelper = DBHelper(this)
        val cursor = dbHelper.show()
        if(cursor.count==0){
            Snackbar.make(binding.mainactivityID,"Do not Fount Data",Snackbar.LENGTH_LONG).show()
        }
        else{
            while(cursor.moveToNext()){
                val id  = cursor.getString(0).toString()
                val name = cursor.getString(1).toString()
                val email = cursor.getString(2).toString()
                val  user = User(id,name,email)
               userList.add(user)

            }
            myAdapter = MyAdapter(userList,this)
            binding.recyclerviewid.adapter = myAdapter
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.insertbuttonid ->{

                startActivity(Intent(this,InsertActivity::class.java))
            }
            R.id.logoutbuttonid ->{
                val sharedPreferences  = getSharedPreferences("database",Context.MODE_PRIVATE)
                val edit = sharedPreferences.edit()
                edit.apply {
                    putString("status","notLogin")
                }.apply()
                val intent  = Intent(this,Login::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onItemClick(i: Int) {
        Snackbar.make(binding.mainactivityID,""+userList[i].name,Snackbar.LENGTH_LONG).show()
    }

    override fun onLongItemClick(i: Int) {
        val dialogs=AlertDialog.Builder(this)
        val option = arrayOf("Update","Delete")
        dialogs.setTitle("Choose a option")
        dialogs.setItems(option){ dialogInterface: DialogInterface, pos: Int ->

            val select= option[pos]
            if(select=="Update"){
                Snackbar.make(binding.mainactivityID,"List Updated",Snackbar.LENGTH_LONG).show()
            }
            else if(select=="Delete"){
                val id =userList[i].id
                val value= dbHelper.delete(id)
                if(value>0){
                    userList.removeAt(i)
                    myAdapter.notifyItemRemoved(i)
                    Snackbar.make(binding.mainactivityID,"List Deleted",Snackbar.LENGTH_LONG).show()
                }
                else{
                    Snackbar.make(binding.mainactivityID,"List Deleted Failed",Snackbar.LENGTH_LONG).show()
                }


            }

        }
        dialogs.create().show()

    }
}