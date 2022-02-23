package com.Pago.ContactsApp.view

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.Pago.ContactsApp.model.dataModel.ProfileModel
import com.Pago.ContactsApp.model.repository.ProfileRepository
import com.Pago.ContactsApp.model.RetrofitService
import com.Pago.ContactsApp.modelView.ProfileViewModel
import com.Pago.ContactsApp.modelView.factory.ProfileViewModelFactory
import com.PagoContactsApp.R
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        val retrofitService = RetrofitService.getInstance()
        val profileRepository = ProfileRepository(retrofitService)
        val nameTextView: TextView = findViewById(R.id.profile_name)
        val emailTextView: TextView = findViewById(R.id.profile_email)
        val postTitleTextView: TextView = findViewById(R.id.profile_post_title)
        val postBodyTextView: TextView = findViewById(R.id.profile_post_body)
        val imageView: ImageView = findViewById(R.id.profile_imageView)

        var username = ""
        var email = ""
        var userId: Int? = null
        var hasImage = false

        val extras: Bundle? = intent.extras
        if(extras != null){
            username = extras.getString("username")!!
            userId = extras.get("userId") as Int?
            email = extras.getString("email")!!
            hasImage = (extras.get("hasImage") as Boolean?)!!

        }


        nameTextView.text = username
        emailTextView.text = email

        if(hasImage){
            Glide.with(this)
                .load("https://picsum.photos/200/200")
                .transform(CircleCrop())
                .into(imageView)
        }else{
            val userLogoText: String  = username
                .split(" ")
                .filter { word -> !word.contains(".") }
                .map { word -> word[0] }
                .joinToString("")
                .uppercase()
            val drawable: TextDrawable = TextDrawable.builder()
                .buildRound(userLogoText, Color.RED)
            imageView.setImageDrawable(drawable)
        }

        println("userId: $userId")
        if(userId != null) {
            viewModel = ViewModelProvider(
                this,
                ProfileViewModelFactory(profileRepository, userId)
            ).get(
                ProfileViewModel::class.java
            )

            viewModel.userPostsList.observe(this, { posts ->
                if(posts.isNotEmpty()){
                    val firstPost: ProfileModel = posts[0]
                    postTitleTextView.text = firstPost.title
                    postBodyTextView.text = firstPost.body
                    println("not null")
                }

            })

            viewModel.errorMessage.observe(this, {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

            viewModel.getAllUserPosts()
        }

    }
}