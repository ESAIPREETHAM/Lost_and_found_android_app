package com.example.lostandfound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lostandfound.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val email = intent.getStringExtra("email_key")
//        val email2 = email!!.replace('.',',')


//        readData(email)
        userID = FirebaseAuth.getInstance().uid!!
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(userID).get().addOnSuccessListener {
                val fullName = it.child("fullName").value
                val rollNumber = it.child("rollNumber").value
                val phoneNumber = it.child("phoneNumber").value
    //            email.replace(",", ".")

                val nameTextView: TextView = findViewById(R.id.textView2)
                nameTextView.text = fullName as CharSequence?
            }


        binding.btnPostLostItem.setOnClickListener{
            val intent = Intent(this, PostLostItemActivity::class.java)
            startActivity(intent)
        }

        binding.btnPostFoundItem.setOnClickListener{
            val intent = Intent(this, PostFoundItemActivity::class.java)
            startActivity(intent)
        }

        binding.btnFeedLostItem.setOnClickListener{
            val intent = Intent(this, FeedLostItemActivity::class.java)
            startActivity(intent)
        }

        binding.btnFeedFoundItem.setOnClickListener{
            val intent = Intent(this, FeedFoundItemActivity::class.java)
            startActivity(intent)
        }

        binding.btnMyPosts.setOnClickListener{
            val intent = Intent(this, MyPostsActivity::class.java)
            startActivity(intent)
        }

        binding.btnUpdatePass.setOnClickListener{
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }
        binding.textView3.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }


//    private fun readData(email: String?) {
//        database = FirebaseDatabase.getInstance().getReference("Users")
//        database.child(email!!).get().addOnSuccessListener{
//            val fullName = it.child("fullName").value
//            val rollNumber = it.child("rollNumber").value
//            val phoneNumber = it.child("phoneNumber").value
//        }
//    }
}