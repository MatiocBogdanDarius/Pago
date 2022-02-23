package com.PagoContactsApp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop


class MainAdapter(_listener: RecycleViewClickListener)
    : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var contactList = mutableListOf<Contact>()
    var listener = _listener

    fun setContacts(contacts: List<Contact>) {
        this.contactList = contacts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)

        return MainViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val contact = contactList[position]
        var contactNameTextView: TextView = holder.itemView.findViewById(R.id.id_name)
        contactNameTextView.text = contact.name
        if(position % 2 == 0){
            Glide.with(holder.itemView.context)
                .load("https://picsum.photos/200/200")
                .transform(CircleCrop())
                .into(holder.itemView.findViewById(R.id.iv_image))
        }
        else {
            val contactName: String = contactList[position].name
            var userLogoText: String  = contactName
                .split(" ")
                .filter { word -> !word.contains(".") }
                .map { word -> word[0] }
                .joinToString("")
                .uppercase()
            var drawable: TextDrawable = TextDrawable.builder()
                .buildRound(userLogoText, Color.RED)
            var image: ImageView = holder.itemView.findViewById(R.id.iv_image)
            image.setImageDrawable(drawable)
        }


    }

    override fun getItemCount(): Int {
        return contactList.size
    }


    class MainViewHolder(itemView: View, _listener: RecycleViewClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var listener = _listener;

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onClick(itemView, absoluteAdapterPosition)
        }
    }

    interface RecycleViewClickListener{
        fun onClick(view: View, position: Int)
    }
}
