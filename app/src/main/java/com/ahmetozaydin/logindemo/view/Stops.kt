package com.ahmetozaydin.logindemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmetozaydin.logindemo.R
import com.ahmetozaydin.logindemo.databinding.ActivityStopsBinding
import com.ahmetozaydin.logindemo.model.*
import com.ahmetozaydin.logindemo.service.BusAPI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class Stops : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStopsBinding
    private lateinit var location: LatLng

    
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
                    response.body()?.let {
                        stopList = ArrayList(it.stops)
                        for (stop: Stop in stopList!!) {

                            println("name : " + stop.name)
                            println("latitude : " + stop.latitude)
                            println("longitude : " + stop.longitude)

                            location = LatLng(stop.atco_latitude!!,stop.longitude!!)
                            mMap.addMarker(MarkerOptions().position(location).title("${stop.name}"))

                        }

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,100f))

                    }

                }
            }
        })




    }


}