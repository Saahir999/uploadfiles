package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.EditText


import android.widget.TextView
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.UploadTask
import java.net.URL

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;


class Sender: AppCompatActivity() {

    lateinit var dispname: EditText
    lateinit var btnChoose: Button
    lateinit var btnUpload : Button
    lateinit var btnrecycler : Button

    private var filepath : Uri? = null

    //TODO : this might be databaseref = Firebase.database.reference
    var storage: FirebaseStorage? = null
    var storageRef : StorageReference? = null
    var databaseref: DatabaseReference? =null
    var dtb: FirebaseDatabase? = null

   // var analytic = FirebaseAnalytics.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        dispname = findViewById<EditText>(R.id.dispname)
        btnChoose = findViewById<Button>(R.id.btnChoose)
        btnUpload = findViewById<Button>(R.id.btnUpload)
        btnrecycler  = findViewById(R.id.btnrecycler)


        storage = FirebaseStorage.getInstance()
        storageRef = storage!!.reference

        dtb = FirebaseDatabase.getInstance()
        databaseref = dtb!!.reference

        var flag:Boolean = false
        btnChoose.setOnClickListener {
            choose()
        }
        btnUpload.setOnClickListener {
            if(dispname.text!=null)
            {
                flag= true
            }
            if(flag==false)
            {
                Toast.makeText(this,"Please name your upload", Toast.LENGTH_SHORT).show()
            }
            else
            {
                uploadPDF()
            }
        }

        btnrecycler.setOnClickListener {
            Intent( this, Cart::class.java ).also{
                startActivity(it)
            }
        }


    }



    protected fun choose()
    {
        val intent = Intent().setType("application/pdf").setAction(Intent.ACTION_GET_CONTENT)
        //TODO-> request code
        startActivityForResult(Intent.createChooser(intent, "Select a file"), 999)
    }

    override fun onActivityResult(requestcode: Int, resultCode: Int, data: Intent?)
    {


            super.onActivityResult(requestcode, resultCode, data)

            if (requestcode == 999 && resultCode == RESULT_OK && data != null && data.data != null) {

                if (data.data!= null) {
                    //uploading the file
                    filepath = data.data
                } else {
                    Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
                }
            }


    }


    fun uploadPDF()
    {
        if(filepath != null)
        {
            var str = storageRef?.child("uploads/"+dispname.text+".pdf")
            str!!.putFile(filepath!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                    //var upload: Upload = Upload(dispname.text as String, "")

                   /** fun onDataChange(dataSnapshot: DataSnapshot) {
                        //TODO: Download url might be wrong

                        for (databaseref in dataSnapshot.children)
                        {
                        //TODO add .child(userId) after .child("users") probably for authentication
                            databaseref.child("users").child("778").ref.setValue(upload)
                        }
                    } */


                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failure" , Toast.LENGTH_SHORT).show()
                }

        }
        else
        {
            Toast.makeText(this,"Please choose a pdf first",Toast.LENGTH_LONG).show()
        }

    }
}

