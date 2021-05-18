package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlin.collections.List
import com.example.firebase.Upload

class Cart: AppCompatActivity() {

    private lateinit var databaseref: DatabaseReference
    lateinit var rvcart: RecyclerView
    var uplist = mutableListOf<Upload>()
    lateinit var book: Button
    lateinit var img: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.displayit)

        Toast.makeText(this , "Shit Shit", Toast.LENGTH_LONG).show()

        var flag = false
        rvcart = findViewById(R.id.rvcart)

        book = findViewById(R.id.btnBook)
        img = findViewById(R.id.btnImg)
        databaseref = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS + "/users" + "/Books")
        Log.v(this.toString(), databaseref.toString())
        flag = true
        book.setOnClickListener {
            imgflag = false
            databaseref = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS + "users" + "Books")
            flag = true
        }

        img.setOnClickListener {
            databaseref = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS+"users"+"Images")
            imgflag = true
            flag = true
        }

        databaseref.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot)
                    {
                        Log.d(this@Cart.toString(), "1 FIRST/////////")

                        dataSnapshot.children.forEach{
                            Log.d(this@Cart.toString(), "2 SECOND")
                            var upload: Upload = it.value as Upload
                            uplist.add(upload)
                        }
                        Log.d(this@Cart.toString(), "3 kbdvsbkduedcebecackbacbc azbed vlajdkv")
                        val adapter = todoAdapter(uplist)
                        rvcart.adapter = adapter
                        rvcart.layoutManager = LinearLayoutManager(applicationContext)

                    }

                    override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,"Thats no good",Toast.LENGTH_SHORT).show()
                    }
                })



        // It might happen that uplist is passed first initialised later due to this weird flow of control

    }


   /** fun DatabaseReference.addValueEventListener() {
        fun onDataChange(dataSnapshot: DataSnapshot) {


            for (postsnapshot: DataSnapshot in dataSnapshot.children) {
                var upload: Upload = postsnapshot.value as Upload
                uplist.
            }


            //TODO pass it to adapter here

        } */
}


