package com.example.roomdatabasekotlin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasekotlin.databinding.ActivityMainBinding
import com.example.roomdatabasekotlin.databinding.UpdateDialogBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener,MyAdapter.OnItemClick {
    lateinit var dao: UserDAO
    private var userList= ArrayList<User>()
    private lateinit var adapter: MyAdapter
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.setHasFixedSize(true)
        loadData()



    }

    private fun loadData() {
        userList= dao.getUser() as ArrayList<User>
        adapter= MyAdapter(userList,this)
        binding.recycleView.adapter= adapter
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.insertButton->{
                startActivity(Intent(this,insertActivity::class.java))
            }
        }
    }

    override fun onItemClick(i: Int) {
        Snackbar.make(binding.layoutMain,""+userList[i].name,
            Snackbar.LENGTH_LONG).show()
    }

    override fun onLongItemClick(i: Int) {
        val dialogue= AlertDialog.Builder(this)
        val  option= arrayOf("Update","Delete")
        dialogue.setTitle("Choose a option")
        dialogue.setItems(option){ dialogInterface: DialogInterface, i: Int ->

            val select= option[i]
            if(select=="Update"){
                updateData(i)
            }
            else{
                val id= userList[i].id
                val value= dao.delete(id)
                if(value>0){
                    userList.removeAt(i)
                    adapter.notifyItemRemoved(i)
                    Snackbar.make(binding.layoutMain,"Delete Successfully",Snackbar.LENGTH_SHORT).show()

                }
                else{
                    Snackbar.make(binding.layoutMain,"Delete Failed",Snackbar.LENGTH_SHORT).show()

                }

            }
        }
        val alert= dialogue.create().show()
    }
    private fun updateData(position: Int) {

        val dialog= AlertDialog.Builder(this)
        val view= UpdateDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setView(view.root).setTitle("Update").setCancelable(true)
            .setPositiveButton("Update"){ dialogInterface: DialogInterface, i: Int ->

                val id= userList[position].id
                val name= view.updatednameid.text.toString()
                val email= view.updatedemailId.text.toString()
                val value= dao.update(id, name, email)
                if(value>0){
                    Snackbar.make(binding.layoutMain,"Update Successfully",Snackbar.LENGTH_SHORT).show()

                }
                else{
                    Snackbar.make(binding.layoutMain,"Update Filed",Snackbar.LENGTH_SHORT).show()

                }

            }.setNegativeButton("Close"){ dialogInterface: DialogInterface, i: Int ->

            }

        view.updatedId.text= "Updating index no ${userList[position].id}"
        view.updatednameid.setText(userList[position].name)
        view.updatedemailId.setText(userList[position].email)
        dialog.create().show()

    }
}