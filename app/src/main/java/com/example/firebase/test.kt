package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.os.Bundle
import com.example.firebase.R
import android.graphics.drawable.ColorDrawable
import android.content.Intent
import android.app.Activity
import android.graphics.Bitmap
import android.provider.MediaStore
import android.app.ProgressDialog
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.lang.Exception
import java.util.*

class test : AppCompatActivity() {


    private lateinit var btnSelect: Button
    private lateinit var btnUpload: Button


    private lateinit var imageView: ImageView


    private var filePath: Uri? = null
    lateinit var dispname:EditText

    private val PICK_IMAGE_REQUEST = 22
    var databaseref: DatabaseReference? =null
    var dtb: FirebaseDatabase? = null


    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testsupport)

        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(
            Color.parseColor("#0F9D58")
        )
        actionBar!!.setBackgroundDrawable(colorDrawable)


        btnSelect = findViewById(R.id.btnChoose)
        btnUpload = findViewById(R.id.btnUpload)
        imageView = findViewById(R.id.imgView)

        dispname = findViewById(R.id.dispname)
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        dtb = FirebaseDatabase.getInstance()
        databaseref = dtb!!.getReference(Constants.DATABASE_PATH_UPLOADS)


        btnSelect.setOnClickListener(View.OnClickListener { SelectImage() })


        btnUpload.setOnClickListener{
            if(dispname.text.toString()=="")
            {
                Toast.makeText(this,"Please type name of image",Toast.LENGTH_SHORT).show()
            }
            else
            {
                uploadImage()
            }
        }
    }


    private fun SelectImage() {


        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                imageView!!.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }

    // UploadImage method
    private fun uploadImage() {
        if (filePath != null) {


            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()


            val ref = storageReference?.child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            // adding listeners on upload
            // or failure of image
            ref!!.putFile(filePath!!)
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@test,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT
                        )
                        .show()

                    var upload: Upload = Upload(dispname.text.toString(), it.uploadSessionUri.toString())

                    databaseref?.child("users")?.child("Images")?.ref?.setValue(upload)
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@test,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress: Double = (100.0
                            * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount())
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }
        }
    }
}