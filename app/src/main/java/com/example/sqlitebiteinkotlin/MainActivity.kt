package com.example.sqlitebiteinkotlin

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlitebiteinkotlin.databinding.ActivityMainBinding
import com.example.sqlitebiteinkotlin.databinding.UpdateDialogBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener,MyAdapter.OnItemClick {

    lateinit var dbHelper: DBHelper
    lateinit var myAdapter: MyAdapter
    private val userList = ArrayList<User>()
    private val newList = ArrayList<User>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "User List"
        binding.insertButton.setOnClickListener(this)
        //binding.logoutbuttonid.setOnClickListener(this)
        binding.recycleId.layoutManager = LinearLayoutManager(this)
        binding.recycleId.setHasFixedSize(true)
        loadData()

        binding.swipeLayout.setOnRefreshListener {
            userList.clear()
            newList.clear()
            val cursor = dbHelper.show()
            while (cursor.moveToNext()){
                val id  = cursor.getString(0).toString()
                val name = cursor.getString(1).toString()
                val email = cursor.getString(2).toString()
                val  user = User(id,name,email)
                userList.add(user)

            }
            newList.addAll(userList)
            myAdapter = MyAdapter(userList,this)
            binding.recycleId.adapter = myAdapter
            binding.swipeLayout.isRefreshing =false
        }

    }

    private fun loadData() {
         dbHelper = DBHelper(this)
        val cursor = dbHelper.show()
        if(cursor.count==0){
            Snackbar.make(binding.mainLayout,"Do not Fount Data",Snackbar.LENGTH_LONG).show()
        }
        else{
            while(cursor.moveToNext()){
                val id  = cursor.getString(0).toString()
                val name = cursor.getString(1).toString()
                val email = cursor.getString(2).toString()
                val  user = User(id,name,email)
               userList.add(user)

            }
            newList.addAll(userList)
            myAdapter = MyAdapter(newList,this)
            binding.recycleId.adapter = myAdapter
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.insertButton ->{

                startActivity(Intent(this,InsertActivity::class.java))
            }

        }
    }

    override fun onItemClick(i: Int) {
        Snackbar.make(binding.mainLayout,""+newList[i].name,Snackbar.LENGTH_LONG).show()
    }

    override fun onLongItemClick(i: Int) {
        val dialogs=AlertDialog.Builder(this)
        val option = arrayOf("Update","Delete")
        dialogs.setTitle("Choose a option")
        dialogs.setItems(option){ dialogInterface: DialogInterface, pos: Int ->

            val select= option[pos]
            if(select=="Update"){
                updateData(i)
            }
            else if(select=="Delete"){
                val id =newList[i].id
                val value= dbHelper.delete(id)
                if(value>0){
                    newList.removeAt(i)
                    myAdapter.notifyItemRemoved(i)
                    Snackbar.make(binding.mainLayout,"List Deleted",Snackbar.LENGTH_LONG).show()
                }
                else{
                    Snackbar.make(binding.mainLayout,"List Deleted Failed",Snackbar.LENGTH_LONG).show()
                }


            }

        }
        dialogs.create().show()

    }

    private fun updateData(position: Int) {
        val dialog = AlertDialog.Builder(this)
        val view = UpdateDialogBinding.inflate(LayoutInflater.from(this))
        dialog.setView(view.root).setTitle("Update Data").setCancelable(true).
        setPositiveButton("Update"){ dialogInterface: DialogInterface, i: Int ->

            val id = newList[position].id
            val name =view.updatednameid.text.toString()
            val email = view.updatedemailId.text.toString()
            val user= User(id,name,email)
            val value  = dbHelper.update(user)
            if(value>0){
                Snackbar.make(binding.mainLayout,"Updated Data Successfully",Snackbar.LENGTH_LONG).show()
            }
            else{
                Snackbar.make(binding.mainLayout,"Updated Data Failed",Snackbar.LENGTH_LONG).show()
            }
        }
        dialog.setNegativeButton("Close"){ dialogInterface: DialogInterface, i: Int ->

        }
        view.updatedId.text = "Updating Id Number${newList[position].id}"
        view.updatednameid.setText(newList[position].name)
        view.updatedemailId.setText(newList[position].email)

        dialog.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.aboutid ->{

                Snackbar.make(binding.mainLayout,"This is about page",Snackbar.LENGTH_LONG).show()
            }

            R.id.menulogoutid ->{


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
            R.id.searchId ->{

                val searchView =item.actionView as SearchView
                searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                       if(newText!!.isNotEmpty()){

                           newList.clear()
                           val txt = newText.toLowerCase(Locale.getDefault())
                           userList.forEach{
                               if (it.name.toLowerCase(Locale.getDefault()).contains(txt) || it.email.toLowerCase(Locale.getDefault()).contains(txt))
                               {
                                   newList.add(it)
                               }
                           }
                           binding.recycleId.adapter?.notifyDataSetChanged()
                       }
                       else{

                           newList.clear()
                           newList.addAll(userList)
                           binding.recycleId.adapter?.notifyDataSetChanged()
                       }
                        return true
                    }

                })
            }
        }
        return super.onOptionsItemSelected(item)
    }
}