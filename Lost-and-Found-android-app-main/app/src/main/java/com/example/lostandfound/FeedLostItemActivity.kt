package com.example.lostandfound

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.databinding.ActivityFeedLostItemBinding
import com.google.firebase.firestore.*


class FeedLostItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedLostItemBinding
    private lateinit var userArrayList: ArrayList<LostItems>
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapterLostItems: MyAdapterLostItems
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedLostItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recyclerview_LostFeed)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        myAdapterLostItems = MyAdapterLostItems(this, userArrayList)

        recyclerView.adapter = myAdapterLostItems

        eventChangeListener()

        binding.textView4.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun eventChangeListener(){

        db = FirebaseFirestore.getInstance()
        db.collection("Lost Items").addSnapshotListener(object: EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error!= null){
                    Log.e("Firestore Error", error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(LostItems::class.java))
                    }
                }
                myAdapterLostItems.notifyDataSetChanged()
            }

        })

    }

}








//        val adapter = GroupieAdapter()
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//
//        binding.recyclerviewLostFeed.adapter = adapter

//        fetchItems()
//
//        binding.textView4.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun fetchItems() {
//        val db = FirebaseFirestore.getInstance()
//        db.collection("Lost Items").addSnapshotListener(object : EventListener<QuerySnapshot> {
//
//            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
//                val adapter = GroupieAdapter()
//                if (error != null) {
//                    Log.e("Firestore Error", error.message.toString())
//                    return
//                }
//                for (dc : DocumentChange in value?.documentChanges!!) {
//
//                    if (dc.type == DocumentChange.Type.ADDED){
//                        userArrayList?.add(dc.document.toObject(Items::class.java))
//                    }
//                    adapter.notifyDataSetChanged()
//
//
//
//                }
//                binding.recyclerviewLostFeed.adapter = adapter
//
//
//
//
//            }
//
//        })
//
//
//    }
//}


//if (error != null) {
//    Log.e("Firestore Error", error.message.toString())
//    return
//}
//
//for (documentChange in documentSnapshots.documentChanges) {
//    val isAttendance = documentChange.document.data["Attendance"].toString()
//    val isCalender = documentChange.document.data["Calender"].toString()
//    val isEnablelocation =
//        documentChange.document.data["Enable Location"].toString()


//class UserItem(private val items: Items): Item<GroupieViewHolder>() {
//
//    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        viewHolder.itemView.findViewById<TextView>(R.id.textView7).text = items.fullName
//        viewHolder.itemView.findViewById<TextView>(R.id.textView8).text = items.phoneNumber
//        viewHolder.itemView.findViewById<TextView>(R.id.textView9).text = items.message
//    }
//
//    override fun getLayout(): Int {
//        return R.layout.lost_feed_row
//    }
//}