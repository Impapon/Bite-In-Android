package com.example.sqlitebiteinkotlin

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.lang.Exception

class DBHelper(private val context: Context):SQLiteOpenHelper(context, DB_NAME,null, VERSION){

    companion object{
        const val DB_NAME = "mydatabase"
        const val TABLE_NAME = "userTable"
        const val NAME = "name"
        const val EMAIL="email"
        const val ID = "id"
        const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val create_table = "CREATE TABLE "+ TABLE_NAME +" " +
               "("+ ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ NAME+ " VARCHAR(100),"+ EMAIL +" VARCHAR(100)); "

        try{
            db?.execSQL(create_table)
            Toast.makeText(context, "Table Created", Toast.LENGTH_LONG).show()

        }catch (e:Exception){

            Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        val upgrade = "DROP TABLE IF EXISTS $TABLE_NAME"
        try{
            db?.execSQL(upgrade)
            Toast.makeText(context, "Table Upgraded", Toast.LENGTH_LONG).show()

        }catch (e:Exception){

            Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show()
        }

    }

    fun insert(name:String,email:String):Long{
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("NAME",name)
        contentValues.put("EMAIL",email)
        return  db.insert(TABLE_NAME,null,contentValues)
    }
    fun show():Cursor{
        val db = readableDatabase
        val getdata = "SELECT * FROM $TABLE_NAME"
        return db.rawQuery(getdata,null)
    }
    fun delete(id:String):Int{
        val db = writableDatabase
        return db.delete(TABLE_NAME,"$ID=?", arrayOf(id))
    }

}