package com.example.roomdatabasekotlin

import androidx.room.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:User):Long

    @Transaction
    @Query("SELECT * FROM user")
    fun getUser():List<User>

    @Transaction
    @Query("DELETE FROM user WHERE id = :id")
    fun delete(id:Int?):Int

    @Transaction
    @Query("UPDATE user SET name = :name, email = :email WHERE id = :id")
    fun update(id:Int?,name:String, email:String):Int
}