package com.ahmetozaydin.logindemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmetozaydin.logindemo.databinding.FragmentLinesBinding
import com.ahmetozaydin.logindemo.model.Point
import com.ahmetozaydin.logindemo.model.ServiceModel
import com.ahmetozaydin.logindemo.service.ServiceAPI
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LinesFragment : Fragment() {
    private lateinit var  binding  : FragmentLinesBinding
    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        fetchData()
    }
    private fun fetchData() {

        runnable = object : Runnable {
            override fun run() {

                val retrofit = Retrofit.Builder()
                    .baseUrl(ServiceActivity.BASE_URL)
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
                                var counter = 0
                                val list = ArrayList<Point>()
                                serviceModel.services?.forEach { services ->
                                    services.routes?.forEach { route ->
                                        route.points?.forEach { point ->
                                            // Log.e("TAG",point.stopID.orEmpty())
                                            list.add(point)
                                            val location = LatLng(point.latitude,point.longitude)
                                            //mMap.addMarker(MarkerOptions().position(location).title("${point.stopID}"))


                                                   println(point.latitude)
                                               println(point.longitude)
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