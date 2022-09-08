package com.ahmetozaydin.logindemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetozaydin.logindemo.adapter.LinesAdapter
import com.ahmetozaydin.logindemo.databinding.FragmentLinesBinding
import com.ahmetozaydin.logindemo.model.ServiceModel
import com.ahmetozaydin.logindemo.model.Services
import com.ahmetozaydin.logindemo.service.ServiceAPI
import com.ahmetozaydin.logindemo.view.LinesToMap
import com.ahmetozaydin.logindemo.view.Stops.Companion.BASE_URL
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.rcycler_view_stops.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LinesFragment : Fragment(), LinesAdapter.Listener {
    private lateinit var binding: FragmentLinesBinding
    private var linesAdapter: LinesAdapter? = null
    private var servicesList = ArrayList<Services>()
    private var databaseList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //sharedPreferences.edit()?.putInt("count", 0)?.apply()
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
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = layoutManager
        fetchData()
    }

    private fun fetchData() {
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
                        // val list = ArrayList<Point>()
                        serviceModel.services?.forEach { services ->
                            //val servicesList = arrayListOf(services.name)
                            //servicesList.add(services.name.toString())
                            // servicesList = ArrayList<Services>()
                            servicesList.add(services)

                        }//burası patlatabilir belki
                        /*  binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                          val adapter = LinesAdapter(servicesList,this@LinesFragment)//?
                          binding.recyclerView.adapter = adapter*/
                    }
                    linesAdapter = LinesAdapter(servicesList, this@LinesFragment)
                    binding.recyclerView.adapter = linesAdapter
                    context?.let {
                        createSwipeGesture(
                            it.applicationContext,
                            binding.recyclerView
                        )
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
                    }
                }
            }
        })
    }

    override fun onItemClick(services: Services) {
        val intent = Intent(activity, LinesToMap::class.java)
        intent.putExtra("line_name", services.description)
        intent.putExtra("Service", services)
        startActivity(intent)
    }

    fun createSwipeGesture(context: Context, recyclerView: RecyclerView) {
        val swipeGestures = object : SwipeGestures(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        try {
                            val myDatabase = requireActivity().openOrCreateDatabase(
                                "MyDatabase",
                                Context.MODE_PRIVATE,
                                null
                            )
                            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS descriptions_table (column_description VARCHAR NOT NULL UNIQUE)")
                            /*val sharedPreferences = activity?.getSharedPreferences(
                                "com.ahmetozaydin.logindemo",
                                Context.MODE_PRIVATE
                            )!!*/
                            //val isExecuted = sharedPreferences.getBoolean("isExecuted", true)
                           //  val rowName = services.description?.replace("'", "''")TODO()
                            myDatabase.execSQL("INSERT INTO descriptions_table(column_description) VALUES ('${viewHolder.itemView.description.text}')")//TODO
                            //sharedPreferences.edit().putBoolean("isExecuted", false).apply()
                            val cursor =
                                myDatabase.rawQuery(("SELECT * FROM descriptions_table"), null)
                            val examplesIx = cursor.getColumnIndex("column_description")
                            while (cursor.moveToNext()) {
                                //println("Text: " + cursor.getString(examplesIx))
                                if(!databaseList.contains(cursor.getString(examplesIx))){
                                    databaseList.add(cursor.getString(examplesIx))
                                }
                            }
                            cursor.close()
                            println(databaseList.size)
                            databaseList.forEach {
                                println(it)
                            }
                            //databaseList.add(viewHolder.itemView.description.text.toString())
                            /* bundle.putStringArrayList("databaseList",databaselist)
                             parentFragmentManager.setFragmentResult("data",bundle)
                             favoritesFragment.arguments*/
                            val bundle = Bundle()
                            bundle.putStringArrayList("arrayList",databaseList)
                        } catch (exception: Exception) {
                            exception.printStackTrace()
                        }
                        recyclerView.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                    }
                    /*  ItemTouchHelper.RIGHT->{
                      }*/
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGestures)
        touchHelper.attachToRecyclerView(recyclerView)
    }
}

