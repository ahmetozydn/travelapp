package com.ahmetozaydin.logindemo.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.ahmetozaydin.logindemo.R
import com.ahmetozaydin.logindemo.databinding.ActivityStopsBinding
import com.ahmetozaydin.logindemo.model.*
import com.ahmetozaydin.logindemo.service.BusAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Stops : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStopsBinding
    private lateinit var location: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient






    var stopResponseList: ArrayList<StopResponse>? = null
    var stopList: ArrayList<Stop>? = null

    companion object {
        const val BASE_URL = "https://tfe-opendata.com/api/v1/"

    }


    //https://tfe-opendata.com/api/<v1>  --> baseURL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        getLastKnownLocation()



        val service = retrofit.create(BusAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<StopResponse> {

            override fun onFailure(call: Call<StopResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<StopResponse>,
                response: Response<StopResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        stopList = ArrayList(it.stops)
                        var counter = 0
                        for (stop: Stop in stopList!!) {
                            counter++
                            /*println("name : " + stop.name)
                            println("latitude : " + stop.latitude)
                            println("longitude : " + stop.longitude)*/
                            location = LatLng(stop.latitude!!,stop.longitude!!)
                            //mMap.addMarker(MarkerOptions().position(location).title("${stop.name}"))
                            val bitmap =
                                baseContext.let { AppCompatResources.getDrawable(this@Stops,R.drawable.vector_stop)!!.toBitmap() }

                            mMap.addMarker(
                                MarkerOptions()
                                    .position(location)
                                    .alpha(0.8f)
                                    .snippet("${location.longitude}\n" +
                                            "${location.latitude}")
                                    .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(it) })
                                    .title("User Location"))

                            if(counter ==10 ){
                                break
                            }
                        }
                        getLastKnownLocation()
                    }
                }
            }
        })




    }
    fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    // use your location object
                    // get latitude , longitude and other info from this
                    val userLocation = LatLng(location.latitude,location.longitude)
                    println(userLocation.latitude)
                    println(userLocation.longitude)
                    println(userLocation.toString())
                    val bitmap =
                        baseContext.let { AppCompatResources.getDrawable(this@Stops,R.drawable.vector_user_location)!!.toBitmap() }

                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .snippet("${location.longitude}\n" +
                                    "${location.latitude}")
                            .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(it) })
                            .title("Your Location"))
                }

            }

    }



}