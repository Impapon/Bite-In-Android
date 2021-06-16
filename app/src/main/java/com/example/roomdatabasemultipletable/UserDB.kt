package com.example.roomdatabasemultipletable

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= [

Student::class,
Department::class,
StudentDepartment::class
],version = 1)
abstract class UserDB: RoomDatabase() {

    abstract val myDao: UserDAO

    companion object {

        @Volatile
        private var instance: UserDB? = null

        fun getInstance(context: Context): UserDB {
            synchronized(this) {
                return instance?: Room.databaseBuilder(context.applicationContext,
                UserDB::class.java,"user_db").allowMainThreadQueries().build().also {
                    instance=it
                }
            }
        }
    }
}



