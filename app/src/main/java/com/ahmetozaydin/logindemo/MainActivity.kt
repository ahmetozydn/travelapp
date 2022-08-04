package com.ahmetozaydin.logindemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahmetozaydin.logindemo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,MainScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.logInButton.setOnClickListener {
            val accountName = binding.editTextAccountName.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (accountName == "" || password == "") {
                Toast.makeText(applicationContext, "Boxes cannot be left blank!", Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(accountName,password).addOnSuccessListener{
                    val intent = Intent(this@MainActivity,MainScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this@MainActivity, MainScreenActivity::class.java)
                startActivity(intent)
                finish()
            }


        }

        binding.signUpButton.setOnClickListener {
            val accountName = binding.editTextAccountName.text.toString()
            val password = binding.editTextPassword.text.toString()
            if (accountName == "" || password == "") {
                Toast.makeText(applicationContext, "Boxes cannot be left blank!", Toast.LENGTH_LONG)
                    .show()

            } else {
                auth.createUserWithEmailAndPassword(accountName, password)
                    .addOnSuccessListener {
                        val intent = Intent(this@MainActivity, MainScreenActivity::class.java)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
            }
        }
    }
}




