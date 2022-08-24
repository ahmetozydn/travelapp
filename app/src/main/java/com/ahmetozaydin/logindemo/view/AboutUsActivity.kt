package com.ahmetozaydin.logindemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmetozaydin.logindemo.MenuFragment
import com.ahmetozaydin.logindemo.R
import com.ahmetozaydin.logindemo.databinding.ActivityAboutUsActvityBinding

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutUsActvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsActvityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonBack.setOnClickListener{
            finish()
        }
    }
}