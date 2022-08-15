package com.ahmetozaydin.logindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ahmetozaydin.logindemo.databinding.ActivityServiceBinding
import com.ahmetozaydin.logindemo.model.*
import com.ahmetozaydin.logindemo.service.BusAPI
import com.ahmetozaydin.logindemo.service.ServiceAPI
import com.ahmetozaydin.logindemo.view.Stops
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityServiceBinding
    private var location : LatLng? = null
    private var servicesList : ArrayList<Services>? = null




    companion object {
        const val BASE_URL = "https://tfe-opendata.com/api/v1/"
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        val service = retrofit.create(ServiceAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<ServiceModel> {

            override fun onFailure(call: Call<ServiceModel>, t: Throwable) {
                t.printStackTrace()
            }
            override fun onResponse(
                call: Call<ServiceModel>,
                response: Response<ServiceModel>
            ) {

                if (response.isSuccessful) {
                    response.body()?.let {
                         //pointList = arrayListOf<Point>()
                        servicesList = ArrayList(it.services)

                        for (services:Services in servicesList!!) {
                            println(services.name)
                            println(services.routes)
                                /*val location = LatLng(services.latitude,services.longitude)
                                mMap.addMarker(MarkerOptions().position(location!!).title("${services.stopID}"))*/
                                }
                        }
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!,100f))
                    }
                }
            })
    }
}

