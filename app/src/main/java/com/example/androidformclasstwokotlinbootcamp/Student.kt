package com.example.androidformclasstwokotlinbootcamp

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(val uri:Uri?,val name:String?,val email:String?,val gender:String?
,val date:String?,val time:String?,val blood:String?,val skill:String?
):Parcelable