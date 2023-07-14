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
import com.example.lostandfound.databinding.ActivityPostFoundItemBinding
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

class PostFoundItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostFoundItemBinding
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private  var foundImage1: Uri? = null
    private  var foundImage2: Uri? = null
    private  var foundImage3: Uri? = null
    private  var foundImage4: Uri? = null
    private  var foundImage5: Uri? = null

    private var foundImage1Url: String? = null
    private var foundImage2Url: String? = null
    private var foundImage3Url: String? = null
    private var foundImage4Url: String? = null
    private var foundImage5Url: String? = null

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMessage: EditText
    private lateinit var etWhereFound: EditText

    private var db = Firebase.firestore

    private lateinit var userID: String
    private lateinit var fullName: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostFoundItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()
        userID = FirebaseAuth.getInstance().uid!!

        etName = findViewById(R.id.nameET)
        etPhoneNumber = findViewById(R.id.whatsappET)
        etMessage = findViewById(R.id.messageET)
        etWhereFound = findViewById(R.id.locationET)

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
            if (foundImage1 != null || foundImage2 != null || foundImage3 != null || foundImage4 != null || foundImage5 != null) {
                uploadImage()
            }
        }

        binding.btnSubmit.setOnClickListener {

            val sname = etName.text.toString().trim()
            val sphoneNumber = etPhoneNumber.text.toString().trim()
            val slocationFound = etWhereFound.text.toString().trim()
            val smessage = etMessage.text.toString().trim()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)

            val usermap = hashMapOf(
                "fullName" to sname,
                "phoneNumber" to sphoneNumber,
                "locationFound" to slocationFound,
                "message" to smessage,
                "image1Url" to foundImage1Url,
                "image2Url" to foundImage2Url,
                "image3Url" to foundImage3Url,
                "image4Url" to foundImage4Url,
                "image5Url" to foundImage5Url
            )

            db.collection("user").document(userID).collection("Found Items").document(fileName)
                .set(usermap)
            db.collection("Found Items").document(fileName).set(usermap)
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
            foundImage1 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, foundImage1)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView1.setBackgroundDrawable(bitmapDrawable)
            binding.imageView1.setImageURI(foundImage1)
        }
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            foundImage2 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, foundImage2)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView2.setBackgroundDrawable(bitmapDrawable)
            binding.imageView2.setImageURI(foundImage2)
        }
        if (requestCode == 3 && resultCode == Activity.RESULT_OK && data != null) {
            foundImage3 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, foundImage3)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView3.setBackgroundDrawable(bitmapDrawable)
            binding.imageView3.setImageURI(foundImage3)
        }
        if (requestCode == 4 && resultCode == Activity.RESULT_OK && data != null) {
            foundImage4 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, foundImage4)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView4.setBackgroundDrawable(bitmapDrawable)
            binding.imageView4.setImageURI(foundImage4)
        }
        if (requestCode == 5 && resultCode == Activity.RESULT_OK && data != null) {
            foundImage5 = data?.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,foundImage5)
            val bitmapDrawable = BitmapDrawable(bitmap)
            binding.imageView5.setBackgroundDrawable(bitmapDrawable)
            binding.imageView5.setImageURI(foundImage5)
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

        if(foundImage1 != null){
            storage1.putFile(foundImage1!!).addOnSuccessListener {
                Toast.makeText(this, "1st Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage1.downloadUrl.addOnSuccessListener {
                    foundImage1Url = it.toString()
                }
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(foundImage2 != null) {
            storage2.putFile(foundImage2!!).addOnSuccessListener {
                Toast.makeText(this, "2nd Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage2.downloadUrl.addOnSuccessListener {
                    foundImage2Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(foundImage3 != null) {
            storage3.putFile(foundImage3!!).addOnSuccessListener {
                Toast.makeText(this, "2nd Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage3.downloadUrl.addOnSuccessListener {
                    foundImage3Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(foundImage4 != null) {
            storage4.putFile(foundImage4!!).addOnSuccessListener {
                storage4.downloadUrl.addOnSuccessListener {
                    Toast.makeText(this, "2nd Image uploaded", Toast.LENGTH_SHORT)
                        .show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    foundImage4Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

        if(foundImage5 != null) {
            storage5.putFile(foundImage5!!).addOnSuccessListener {
                Toast.makeText(this, "2nd Image uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
                storage5.downloadUrl.addOnSuccessListener {
                    foundImage5Url = it.toString()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }

    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}