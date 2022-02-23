package com.Pago.ContactsApp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.Pago.ContactsApp.model.repository.MainRepository
import com.Pago.ContactsApp.model.RetrofitService
import com.Pago.ContactsApp.modelView.MainViewModel
import com.Pago.ContactsApp.modelView.factory.MainViewModelFactory
import com.PagoContactsApp.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
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
        progressDialog = findViewById(R.id.progressDialog)
        setOnClickListener()
        adapter = MainAdapter(listener)

        recyclerview.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(mainRepository)
        ).get(
            MainViewModel::class.java
        )


        viewModel.contactList.observe(this, { contacts ->
            var filteredContacts = contacts.filter { contact -> contact.status == "active" }
            filteredContacts = filteredContacts.sortedBy { contact -> contact.name }
            adapter.setContacts(filteredContacts)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.loading.observe(this, {
            if (it) {
                progressDialog.visibility = View.VISIBLE
            } else {
                progressDialog.visibility = View.GONE
            }
        })

        viewModel.getAllContacts()
    }

    private fun setOnClickListener(){
        listener =  object: MainAdapter.RecycleViewClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(applicationContext, ProfileActivity::class.java)
                intent.putExtra("username", adapter.contactList[position].name)
                intent.putExtra("email", adapter.contactList[position].email)
                intent.putExtra("userId", adapter.contactList[position].id)
                val hasImage: Boolean = position % 2 == 0
                intent.putExtra("hasImage", hasImage)
                startActivity(intent)
             }
        }
    }
}