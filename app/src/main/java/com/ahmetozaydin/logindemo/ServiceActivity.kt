package com.ahmetozaydin.logindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ahmetozaydin.logindemo.databinding.ActivityServiceBinding
import com.ahmetozaydin.logindemo.model.*
import com.ahmetozaydin.logindemo.service.ServiceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityServiceBinding
    private var location: LatLng? = null
    private var servicesList: ArrayList<ServiceModel>? = null

    // private var pointList = ArrayList<Point>()
    private var serviceList: ArrayList<Services>? = null

   // private var pointList = ArrayList<Point>()
    private var routeLists = ArrayList<Route>()
    private var routeObject: Route? = null
    private var pointList : ArrayList<Point>? = null



    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())


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

        fetchData()


    }

    private fun fetchData() {

        runnable = object : Runnable {
            override fun run() {

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ServiceAPI::class.java)
                val call = service.loadData()

                call.enqueue(object : Callback<ServiceModel> {

                    override fun onFailure(call: Call<ServiceModel>, t: Throwable) {
                        t.printStackTrace()
                        println("an error occurred")
                    }

                    override fun onResponse(
                        call: Call<ServiceModel>,
                        response: Response<ServiceModel>
                    ) {


                        if (response.isSuccessful) {
                            response.body()?.let { serviceModel ->
                               // pointList = routeObject?.points as ArrayList<Point>
                               // pointList = routeObject?.points as ArrayList<Point>?
                               // pointList = routeObject?.points?.let { ArrayList(routeObject!!.points!!) }
                                val list = ArrayList<Point>()
                                serviceModel.services?.forEach { services ->
                                    services.routes?.forEach { route ->
                                        route.points?.forEach { point ->
                                           // Log.e("TAG",point.stopID.orEmpty())
                                            list.add(point)
                                            val location = LatLng(point.latitude,point.longitude)
                                            mMap.addMarker(MarkerOptions().position(location).title("${point.stopID}"))



                                           //         println(point.latitude)
                                         //   println(point.longitude)
                                        }
                                    }
                                }

                                //serviceList = ArrayList(ServiceModel.services)

                                //pointList = arrayListOf<Point>
                                // servicesList = arrayListOf(it)
                                //pointList = ArrayList()


                                /*for (services: Services in serviceList!!) {

                                    println(services.name)
                                    println("hello world")
                                }*/
                            }
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location!!,100f))
                        }
                    }
                })
                handler.postDelayed(this, 1500000000)
            }

        }
        handler.post(runnable)


    }
}

