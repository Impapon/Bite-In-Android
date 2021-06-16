package com.example.roomdatabasemultipletable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDepartment(department: Department):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentDepartment(studentDepartment: StudentDepartment):Long
}