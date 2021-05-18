package com.example.firebase

import android.text.Editable
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


var imgflag: Boolean = false
var database : FirebaseDatabase = FirebaseDatabase.getInstance()
lateinit var finaldatabaseref : DatabaseReference
class Upload {
    var name: String
    var url: String

    constructor(nam: String = "file" + UUID.randomUUID(), ur: String) {
        name = nam
        url = ur
    }


    fun getNames(): String {
        return (name)
    }

    fun getAurl(): String {
        return (url)
    }
}