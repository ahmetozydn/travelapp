package com.ahmetozaydin.logindemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.adapter.LinesAdapter
import com.ahmetozaydin.logindemo.databinding.FragmentLinesBinding
import com.ahmetozaydin.logindemo.model.ServiceModel
import com.ahmetozaydin.logindemo.model.Services
import com.ahmetozaydin.logindemo.service.ServiceAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LinesFragment : Fragment(),LinesAdapter.Listener{
    private lateinit var  binding  : FragmentLinesBinding
    private var linesAdapter : LinesAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinesBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        fetchData()
    }
    private fun fetchData() {



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
                        val servicesList = ArrayList<Services>()
                        // val list = ArrayList<Point>()
                        serviceModel.services?.forEach { services ->
                            //val servicesList = arrayListOf(services.name)
                            //servicesList.add(services.name.toString())
                            // servicesList = ArrayList<Services>()
                            servicesList.add(services)
                            linesAdapter = LinesAdapter(servicesList,this@LinesFragment)
                            binding.recyclerView.adapter = linesAdapter

                          /*  binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                            val adapter = LinesAdapter(servicesList,this@LinesFragment)//?
                            binding.recyclerView.adapter = adapter*/

                        }




                        /*   serviceModel.services?.forEach { services ->
                               services.routes?.forEach { route ->
                                   route.points?.forEach { point ->
                                       // Log.e("TAG",point.stopID.orEmpty())
                                       list.add(point)
                                       //val location = LatLng(point.latitude,point.longitude)
                                       //mMap.addMarker(MarkerOptions().position(location).title("${point.stopID}"))
                                           binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                                           val adapter = LinesAdapter(list)
                                           binding.recyclerView.adapter = adapter
                                             // println(point.latitude)
                                         // println(point.longitude)
                                   }
                               }
                           }*/

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





    }

    override fun onItemClick(services: Services) {

        Toast.makeText(requireActivity(),"${services.name} item clicked",Toast.LENGTH_LONG).show()


    }


}