package com.PagoContactsApp

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class ProfileActivity : AppCompatActivity() {
    lateinit var viewModel: ProfileViewModel

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

        var username:String = "";
        var email:String = "";
        var userId: Int? = null
        var hasImage: Boolean = false

        val extras: Bundle? = intent.extras;
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
            var userLogoText: String  = username
                .split(" ")
                .filter { word -> !word.contains(".") }
                .map { word -> word[0] }
                .joinToString("")
                .uppercase()
            var drawable: TextDrawable = TextDrawable.builder()
                .buildRound(userLogoText, Color.RED)
            var image: ImageView = findViewById(R.id.profile_imageView)
            image.setImageDrawable(drawable)
        }

        println("userId: $userId")
        if(userId != null) {
            viewModel = ViewModelProvider(
                this,
                ProfileViewModelFactory(profileRepository, userId)).get(ProfileViewModel::class.java
            )

            viewModel.userPostsList.observe(this, { posts ->
                println(posts)
                println(!posts.isEmpty())
                if(!posts.isEmpty()){
                    var firstPost: ProfileModel = posts.get(0)
                    postTitleTextView.text = firstPost.title
                    postBodyTextView.text = firstPost.body
                    println("not null")
                }

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

            viewModel.getAllUserPosts()
        }

    }
}