package com.ahmetozaydin.logindemo

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ahmetozaydin.logindemo.BusLocation.Companion.BASE_URL
import com.ahmetozaydin.logindemo.databinding.ActivityBusLocationBinding
import com.ahmetozaydin.logindemo.databinding.ActivityStopsBinding
import com.ahmetozaydin.logindemo.model.BusLocationModel
import com.ahmetozaydin.logindemo.model.BusLocations
import com.ahmetozaydin.logindemo.model.Stop
import com.ahmetozaydin.logindemo.model.StopResponse
import com.ahmetozaydin.logindemo.service.BusAPI
import com.ahmetozaydin.logindemo.service.BusLocationsAPI
import com.ahmetozaydin.logindemo.view.Stops
import com.ahmetozaydin.logindemo.view.Stops.Companion.BASE_URL
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusLocation : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding:ActivityBusLocationBinding
    private lateinit var mMap: GoogleMap
    private var busLocationsList : ArrayList<BusLocations>? = null
    private lateinit var location :LatLng

    companion object {
     const val BASE_URL = "https://tfe-opendata.com/api/v1/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityBusLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val retrofit = Retrofit.Builder()
            .baseUrl(Stops.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(BusLocationsAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<BusLocationModel> {

            override fun onFailure(call: Call<BusLocationModel>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BusLocationModel>,
                response: Response<BusLocationModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        busLocationsList = ArrayList(it.vehicles)
                        var counter = 0
                        for (bus: BusLocations in busLocationsList!!) {
                            counter++
                            /*println("name : " + stop.name)
                            println("latitude : " + stop.latitude)
                            println("longitude : " + stop.longitude)*/
                            if(counter == 30)
                                break
                            location = LatLng(bus.latitude!!,bus.longitude!!)
                            mMap.addMarker(MarkerOptions().position(location).title("${bus.longitude}"))
                            println("@${bus.destination}")

                        }
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,3f))
                    }
                }
            }
        })



    }
}