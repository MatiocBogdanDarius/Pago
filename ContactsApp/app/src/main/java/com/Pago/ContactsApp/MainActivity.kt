package com.PagoContactsApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var listener: MainAdapter.RecycleViewClickListener
    private lateinit var adapter: MainAdapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressDialog: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        recyclerview = findViewById(R.id.recyclerview)
//        progressDialog = findViewById(R.id.progressDialog)
        setOnClickListener()
        adapter = MainAdapter(listener)

        recyclerview.adapter = adapter

        viewModel = ViewModelProvider(this, MainViewModelFactory(mainRepository)).get(MainViewModel::class.java)


        viewModel.contactList.observe(this, { contacts ->
            var filteredContacts = contacts.filter { contact -> contact.status == "active" }
            filteredContacts = filteredContacts.sortedBy { contact -> contact.name }
            adapter.setContacts(filteredContacts)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, Observer {
            if (it) {
//                progressDialog.visibility = View.VISIBLE
            } else {
//                progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllContacts()
    }

    private fun setOnClickListener(){
        listener =  object: MainAdapter.RecycleViewClickListener{
            override fun onClick(view: View, position: Int) {
                var intent: Intent = Intent(applicationContext, ProfileActivity::class.java)
                intent.putExtra("username", adapter.contactList.get(position).name)
                intent.putExtra("email", adapter.contactList.get(position).email)
                intent.putExtra("userId", adapter.contactList.get(position).id)
                var hasImage: Boolean = position % 2 == 0
                intent.putExtra("hasImage", hasImage)
                startActivity(intent)
             }
        }
    }
}