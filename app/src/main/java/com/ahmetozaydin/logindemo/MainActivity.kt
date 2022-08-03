package com.ahmetozaydin.logindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahmetozaydin.logindemo.databinding.ActivityMainBinding

 private var accountName : String? = null
  private var password : String? = null


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)



        binding.logInButton.setOnClickListener{
            accountName = binding.editTextAccountName.text.toString()
            password = binding.editTextPassword.text.toString()

            if(accountName != null && password != null){//no box is empty// bu if else yapısını kuramadım buraya tekrar bakılacak
                    //use Firebase to control the inputs
            }else{//if one of the box is empty
                Toast.makeText(applicationContext,"Kutucuklar boş bırakılamaz!",Toast.LENGTH_LONG).show()


            }
            val intent = Intent(this@MainActivity,MainScreenActivity::class.java)
            startActivity(intent)
            finish()

        }

    }




}