package com.ahmetozaydin.logindemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ahmetozaydin.logindemo.*
import com.ahmetozaydin.logindemo.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private val favoritesFragment = FavoritesFragment()
    private val homeFragment = HomeFragment()
    private val locationFragment = LocationFragment()
    private val menuFragment = MenuFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        makeCurrentFragment(homeFragment)


        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.location -> makeCurrentFragment(locationFragment)
                R.id.favorites -> makeCurrentFragment(favoritesFragment)
                R.id.menu ->makeCurrentFragment(menuFragment)
                else -> return@setOnItemSelectedListener true

            }
            return@setOnItemSelectedListener true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}