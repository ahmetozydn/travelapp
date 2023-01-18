package com.ahmetozaydin.logindemo.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.ahmetozaydin.logindemo.R
import com.ahmetozaydin.logindemo.databinding.ActivityLinesToMapBinding
import com.ahmetozaydin.logindemo.model.BusInformation
import com.ahmetozaydin.logindemo.model.BusLocationModel
import com.ahmetozaydin.logindemo.model.BusLocations
import com.ahmetozaydin.logindemo.model.Services
import com.ahmetozaydin.logindemo.service.BusLocationsAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LinesToMap : AppCompatActivity(), OnMapReadyCallback {
    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityLinesToMapBinding
    private lateinit var bitmap: Bitmap
    private lateinit var markerPosition: LatLng
    private  var markerList = ArrayList<Marker>()
    private var busLocationsList: ArrayList<BusLocations>? = null
    private var one = 0
    var latlngList = arrayListOf<LatLng>()
    var busLocationArraylist = arrayListOf<BusInformation>()
    var baseBusLocationArrayList = arrayListOf<BusInformation>()
    private lateinit var busInformation: BusInformation
    private lateinit var eachLatLong : LatLng
    private var isTrue : Boolean = true
    private lateinit var eachMarker : LatLng
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinesToMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        bitmap =
            baseContext.let {
                AppCompatResources.getDrawable(this, R.drawable.ic_stop)!!.toBitmap()
            }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.buttonBack.setOnClickListener {
            finish()
        }
        binding.star.setOnClickListener {
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastKnownLocation()
        val lineName = intent.getStringExtra("line_name")
        binding.textViewTitle.text = lineName
        val services = intent.getParcelableExtra<Services>("Service")
        services?.routes?.forEach {
            it.points?.forEach { point ->
                if (point.stopID != null) {
                    /*  markerPosition = LatLng(point.latitude, point.longitude)
                      mMap.addMarker(
                          MarkerOptions()
                              .position(markerPosition)
                              .title(point.stopID)
                              .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(bitmap) })
                      )*/
                    markerPosition = LatLng(point.latitude, point.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(markerPosition).title(point.stopID)
                            .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(bitmap) })
                    )
                        ?.let { it1 -> markerList.add(it1) }
                }
            }
            val builder = LatLngBounds.Builder()
            for (marker in markerList) {
                builder.include(marker.position)
            }
            val bounds = builder.build()
            val padding = 100 // offset from edges of the map in pixels
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.animateCamera(cu);
            //fetchData()
        }
        /* val serviceList : ArrayList<Services>?  = intent.getParcelableExtra("arraylist")

         if (serviceList != null) {
             for (servicess:Services in serviceList){
                 println( servicess.name)
             }
         }*/
    }
    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = LatLng(location.latitude, location.longitude)
                    val bitmap =
                        baseContext.let {
                            AppCompatResources.getDrawable(
                                this,
                                R.drawable.vector_user_location
                            )!!.toBitmap()
                        }
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .icon(bitmap.let { BitmapDescriptorFactory.fromBitmap(it) })
                            .title("Your Location")
                    )
                }
            }
    }
    private fun fetchData() {
    /*    runOnUiThread{
            runnable = object : Runnable {
                override fun run() {
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
                            mMap.clear()
                            if (response.isSuccessful) {
                                mMap.clear()
                                response.body()?.let {
                                    busLocationsList = ArrayList(it.vehicles)
                                    val newLatLng = arrayListOf<LatLng>()
                                    for (bus: BusLocations in busLocationsList!!) {
                                        val aLatLong = LatLng(bus.latitude, bus.longitude)
                                        newLatLng.add(aLatLong)
                                        try {
                                            if (one == 0) {
                                                latlngList.add(aLatLong)
                                            } else {
                                                latlngList.forEachIndexed { index, latLng ->
                                                    if (latlngList[index].latitude != newLatLng[index].latitude || latlngList[index].longitude != newLatLng[index].longitude) {
                                                        val newPosition = LatLng(
                                                            newLatLng[index].latitude,
                                                            newLatLng[index].longitude
                                                        )
                                                        latlngList[index] = newPosition
                                                    }
                                                }
                                            }
                                        } catch (exception: Exception) {
                                            exception.printStackTrace()
                                        }
                                    }
                                    latlngList.forEach { latLng ->
                                        mMap.addMarker(
                                            MarkerOptions()
                                                .position(latLng)
                                                .snippet(
                                                    "${latLng.latitude}\n" +
                                                            "${latLng.latitude}"
                                                )
                                                .icon(bitmap.let {
                                                    BitmapDescriptorFactory.fromBitmap(bitmap)
                                                })
                                                .title("Bus Location")
                                        )
                                    }
                                    one = 1
                                }
                            }
                            if (response.isSuccessful) {
                                response.body()?.let { it ->
                                    it.vehicles.forEach {
                                       busInformation = BusInformation(it.vehicleID,it.latitude,it.longitude,it.destination)
                                       busLocationArraylist.add(busInformation)
                                   }
                                    if(isTrue){
                                        baseBusLocationArrayList.forEach {
                                            baseBusLocationArrayList.add(it)
                                            isTrue = false
                                        }
                                    }
                                    baseBusLocationArrayList.forEachIndexed { index, eachBaseMember ->
                                        busLocationArraylist.forEachIndexed { index, eachTempMember ->
                                            if(eachBaseMember.busId == eachTempMember.busId &&(eachBaseMember.latitude != eachTempMember.latitude || eachBaseMember.longitude != eachTempMember.longitude)){
                                                eachBaseMember.latitude = eachTempMember.latitude
                                                eachBaseMember.longitude = eachBaseMember.longitude
                                            }
                                        }
                                    }
                                    baseBusLocationArrayList.forEach {
                                        eachMarker = it.latitude?.let { it1 -> it.longitude?.let { it2 ->
                                            LatLng(it1,
                                                it2
                                            )
                                        } }!!
                                        mMap.addMarker(MarkerOptions()
                                            .position(eachMarker)
                                            .title(it.busId)
                                        )
                                    }
                                }
                            }
                            busLocationArraylist.forEach {
                                eachLatLong =
                                    busInformation.latitude?.let { it1 -> busInformation.longitude?.let { it2 ->
                                        LatLng(it1,
                                            it2
                                        )
                                    } }!!
                                mMap.addMarker(MarkerOptions().position(eachLatLong).title(it.busId))
                            }
                        }
                    })
                    handler.postDelayed(this, 16000)// this refers to runnable.
                }
            }
            handler.post(runnable)
        }*/
    }
}

