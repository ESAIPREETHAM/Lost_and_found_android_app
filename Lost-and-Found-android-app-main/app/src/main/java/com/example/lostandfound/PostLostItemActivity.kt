package com.example.lostandfound

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.lostandfound.databinding.ActivityPostLostItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class PostLostItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostLostItemBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private  var lostImage1: Uri? = null
    private  var lostImage2: Uri? = null
    private  var lostImage3: Uri? = null
    private  var lostImage4: Uri? = null
    private  var lostImage5: Uri? = null

    private var lostImage1Url: String? = null
    private var lostImage2Url: String? = null
    private var lostImage3Url: String? = null
    private var lostImage4Url: String? = null
    private var lostImage5Url: String? = null

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var etWhereLost: EditText

    private var db = Firebase.firestore

    private lateinit var userID: String
    private lateinit var fullName: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostLostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()
        userID = FirebaseAuth.getInstance().uid!!

        etName = findViewById(R.id.nameET)
        etPhoneNumber = findViewById(R.id.whatsappET)
        etMessage = findViewById(R.id.messageET)
        etWhereLost = findViewById(R.id.locationET)

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userID).get().addOnSuccessListener {
            fullName = it.child("fullName").value as String
            val phoneNumber = it.child("phoneNumber").value

            etName.text = fullName.toString().toEditable()
            etPhoneNumber.text= phoneNumber.toString().toEditable()

        }

        binding.textView2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imageView1.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)

        }

        binding.imageView2.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 2)

        }

        binding.imageView3.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 3)

        }

        binding.imageView4.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 4)

        }

        binding.imageView5.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 5)

        }

        binding.btnUpload.setOnClickListener {
            if (lostImage1 != null || lostImage2 != null || lostImage3 != null || lostImage4 != null || lostImage5 != null) {
                uploadImage()
            }
        }

        binding.btnSubmit.setOnClickListener {

            val sname = etName.text.toString().trim()
            val sphoneNumber = etPhoneNumber.text.toString().trim()
            val slocationLost = etWhereLost.text.toString().trim()
            val smessage = etMessage.text.toString().trim()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)

            val usermap = hashMapOf(
                "fullName" to sname,
                "phoneNumber" to sphoneNumber,
                "locationLost" to slocationLost,
                "message" to smessage,
                "image1Url" to lostImage1Url,
                "image2Url" to lostImage2Url,
                "image3Url" to lostImage3Url,
                "image4Url" to lostImage4Url,
                "image5Url" to lostImage5Url
            )

            db.collection("user").document(userID).collection("Lost Items").document(fileName)
                .set(usermap)
            db.collection("Lost Items").document(fileName).set(usermap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Posted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to Post", Toast.LENGTH_SHORT).show()
                }



            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            lostImage1 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, lostImage1)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView1.setBackgroundDrawable(bitmapDrawable)
            binding.imageView1.setImageURI(lostImage1)
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            lostImage2 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, lostImage2)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView2.setBackgroundDrawable(bitmapDrawable)
            binding.imageView2.setImageURI(lostImage2)
        }
        if (requestCode == 3 && resultCode == Activity.RESULT_OK && data != null) {
            lostImage3 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, lostImage3)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView3.setBackgroundDrawable(bitmapDrawable)
            binding.imageView3.setImageURI(lostImage3)
        }
        if (requestCode == 4 && resultCode == Activity.RESULT_OK && data != null) {
            lostImage4 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, lostImage4)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView4.setBackgroundDrawable(bitmapDrawable)
            binding.imageView4.setImageURI(lostImage4)
        }
        if (requestCode == 5 && resultCode == Activity.RESULT_OK && data != null) {
            lostImage5 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, lostImage5)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView5.setBackgroundDrawable(bitmapDrawable)
            binding.imageView5.setImageURI(lostImage5)
        }

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formatter.format(now)


        val storage1 = FirebaseStorage.getInstance().getReference("images/$fullName/$filename/1")
        val storage2 = FirebaseStorage.getInstance().getReference("images/$fullName/$filename/2")
        val storage3 = FirebaseStorage.getInstance().getReference("images/$fullName/$filename/3")
        val storage4 = FirebaseStorage.getInstance().getReference("images/$fullName/$filename/4")
        val storage5 = FirebaseStorage.getInstance().getReference("images/$fullName/$filename/5")

        if(lostImage1 != null){
            storage1.putFile(lostImage1!!).addOnSuccessListener {
                Toast.makeText(this, "1st Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage1.downloadUrl.addOnSuccessListener {
                    lostImage1Url = it.toString()
                }
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(lostImage2 != null) {
            storage2.putFile(lostImage2!!).addOnSuccessListener {
                Toast.makeText(this, "2nd Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage2.downloadUrl.addOnSuccessListener {
                    lostImage2Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(lostImage3 != null) {
            storage3.putFile(lostImage3!!).addOnSuccessListener {
                Toast.makeText(this, "3rd Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage3.downloadUrl.addOnSuccessListener {
                    lostImage3Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(lostImage4 != null) {
            storage4.putFile(lostImage4!!).addOnSuccessListener {
                storage4.downloadUrl.addOnSuccessListener {
                    Toast.makeText(this, "4th Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    lostImage4Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(lostImage5 != null) {
            storage5.putFile(lostImage5!!).addOnSuccessListener {
                Toast.makeText(this, "5th Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage5.downloadUrl.addOnSuccessListener {
                    lostImage5Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}