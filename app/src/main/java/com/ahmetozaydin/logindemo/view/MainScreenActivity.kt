package com.ahmetozaydin.logindemo.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ahmetozaydin.logindemo.*
import com.ahmetozaydin.logindemo.databinding.ActivityMainScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.google.type.Color
import kotlinx.android.synthetic.main.activity_main_screen.view.*


class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var homeFragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.myToolbar.visibility = View.GONE
        registerLauncher()
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager //casting
        locationListener = LocationListener { location ->
            println("LOCATION : $location")
            println(location.toString())
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) { // android decides here
                Snackbar.make(
                    binding.root,
                    "Permission needed for location",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Give Permission") {
                    //request permission
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }.show()
            } else {
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            return
        } else {
            //permission granted
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1,
                2f,
                locationListener
            )
        }
        makeCurrentFragment(homeFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    makeCurrentFragment(homeFragment)
                    binding.myToolbar.visibility = View.GONE
                }
                R.id.my_card -> {
                    val fragment = MyCardFragment()
                    makeCurrentFragment(fragment)
                    binding.myToolbar.visibility = View.GONE
                }
                R.id.favorites -> {
                    val fragment = FavoritesFragment()
                    makeCurrentFragment(fragment)
                    binding.myToolbar.visibility = View.VISIBLE
                    binding.myToolbar.text_view_inside_toolbar.text = "Favorites"
                }
                R.id.menu -> {
                    val fragment = MenuFragment()
                    makeCurrentFragment(fragment)
                    binding.myToolbar.visibility = View.VISIBLE
                    binding.myToolbar.text_view_inside_toolbar.text = "Menu"
                }
                R.id.lines -> {
                    val fragment = LinesFragment()
                    makeCurrentFragment(fragment)
                    binding.myToolbar.visibility = View.VISIBLE
                    binding.myToolbar.text_view_inside_toolbar.text = "Lines"
                }
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

    private fun registerLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {

                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0f,
                            locationListener
                        )
                    }

                } else {
                    //permission denied
                    Toast.makeText(this, "Permission needed!", Toast.LENGTH_LONG).show()
                }

            }
    }
}