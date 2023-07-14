package com.example.lostandfound

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapterFoundItems(private val context: android.content.Context, private val foundItemsList: ArrayList<FoundItems>) : RecyclerView.Adapter<MyAdapterFoundItems.MyViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterFoundItems.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.found_feed_row, parent, false)

        return MyViewHolder(itemView)

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: MyAdapterFoundItems.MyViewHolder, position: Int) {

        val item: FoundItems = foundItemsList[position]
        holder.fullName.text= item.fullName
        holder.phoneNumber.text = item.phoneNumber
        holder.locationFound.text = item.locationFound
        holder.message.text= item.message
        Glide.with(context).load(item.image1Url).into(holder.image1)
        Glide.with(context).load(item.image2Url).into(holder.image2)
        Glide.with(context).load(item.image3Url).into(holder.image3)
        Glide.with(context).load(item.image4Url).into(holder.image4)
        Glide.with(context).load(item.image5Url).into(holder.image5)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return foundItemsList.size
    }

    // Holds the views for adding it to image and text
    class MyViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val fullName: TextView = itemView.findViewById(R.id.textView7)
        val phoneNumber: TextView = itemView.findViewById(R.id.textView8)
        val locationFound: TextView = itemView.findViewById(R.id.textView9)
        val message: TextView = itemView.findViewById(R.id.textView10)
        val image1: ImageView = itemView.findViewById(R.id.imageView1)
        val image2: ImageView = itemView.findViewById(R.id.imageView2)
        val image3: ImageView = itemView.findViewById(R.id.imageView3)
        val image4: ImageView = itemView.findViewById(R.id.imageView4)
        val image5: ImageView = itemView.findViewById(R.id.imageView5)

    }
}