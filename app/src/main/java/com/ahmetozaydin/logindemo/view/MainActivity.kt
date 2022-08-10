package com.ahmetozaydin.logindemo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahmetozaydin.logindemo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity(){


    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        var accountName: String?
        var password: String?


        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.logInButton.setOnClickListener {
            accountName = binding.editTextAccountName.text.toString()
            password = binding.editTextPassword.text.toString()
            if (accountName == "" || password == "") {
                Toast.makeText(this@MainActivity, "Boxes cannot be left blank!", Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(accountName!!, password!!).addOnSuccessListener {
                    val intent = Intent(this@MainActivity, MainScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@MainActivity, "Login is successful", Toast.LENGTH_LONG)
                        .show()

                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }


            }

        }

        binding.signUpButton.setOnClickListener {
            accountName = binding.editTextAccountName.text.toString()
            password = binding.editTextPassword.text.toString()
            if (accountName == "" || password == "") {
                Toast.makeText(this@MainActivity, "Boxes cannot be left blank!", Toast.LENGTH_LONG)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(accountName!!, password!!)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this@MainActivity,
                            "The user has been Created successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        intent = Intent(this@MainActivity, MainScreenActivity::class.java)
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





