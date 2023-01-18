package com.ahmetozaydin.logindemo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.ahmetozaydin.logindemo.databinding.ActivityBusLocationBinding
import com.ahmetozaydin.logindemo.model.BusInformation
import com.ahmetozaydin.logindemo.model.BusLocationModel
import com.ahmetozaydin.logindemo.service.BusLocationsAPI
import com.ahmetozaydin.logindemo.view.Stops
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class BusLocation : AppCompatActivity(), OnMapReadyCallback {

    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var bitmap: Bitmap
    var tempBusLocationArraylist = arrayListOf<BusInformation>()
    var baseBusLocationArrayList = arrayListOf<BusInformation>()
    private lateinit var busInformation: BusInformation
    private var isTrue: Boolean = true
    private var isExist: Boolean = true
    private lateinit var binding: ActivityBusLocationBinding
    private lateinit var mMap: GoogleMap
    private lateinit var marker: Marker
    private lateinit var eachPosition: LatLng

    companion object {
        const val BASE_URL = "https://tfe-opendata.com/api/v1/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusLocationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        /*bitmap =
            baseContext.let {
                AppCompatResources.getDrawable(
                    this@BusLocation,
                    R.drawable.vector_bus
                )!!.toBitmap()
            }*/
        bitmap = drawableToBitmap(
            ContextCompat.getDrawable(this, R.drawable.vector_bus)!!
        )

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fetchData()
        getLastKnownLocation()
    }

    private fun fetchData() {
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
                        if (response.isSuccessful) {
                            response.body()?.let { it ->
                                it.vehicles.forEach {
                                    eachPosition = LatLng(it.latitude, it.longitude)
                                    marker = mMap.addMarker(
                                        MarkerOptions()
                                            .position(eachPosition)
                                            .snippet("Bus Id")
                                            .title(it.vehicleID)
                                            .icon(fromBitmap(bitmap))
                                    )!!
                                    busInformation = BusInformation(
                                        it.vehicleID,
                                        it.latitude,
                                        it.longitude,
                                        it.destination,
                                        marker
                                    )
                                    tempBusLocationArraylist.add(busInformation)
                                }
                                if (isTrue) {
                                    tempBusLocationArraylist.forEach {
                                        baseBusLocationArrayList.add(it)
                                    }
                                }
                                baseBusLocationArrayList.forEachIndexed { index, eachBaseMember ->
                                    tempBusLocationArraylist.forEachIndexed { index, eachTempMember ->
                                        if ((eachBaseMember.busId.equals(eachTempMember.busId)  && eachBaseMember.marker.position.equals(eachTempMember.marker.position) )
                                        ) {
                                            eachBaseMember.marker.position = eachTempMember.marker.position
                                            eachBaseMember.marker.remove()
                                            mMap.addMarker(
                                                MarkerOptions()
                                                    .position(eachBaseMember.marker.position)
                                                    .snippet("Bus Id")
                                                    .title(eachBaseMember.busId)
                                                    .icon(fromBitmap(bitmap))
                                            )
                                        }
                                    }
                                }
                                tempBusLocationArraylist.clear()
                            }
                            /*if (isExist) {
                                val builder = LatLngBounds.Builder()
                                for (it in baseBusLocationArrayList) {
                                    builder.include(it.marker.position)
                                }
                                val bounds = builder.build()
                                val padding = 10 // offset from edges of the map in pixels
                                val mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                                mMap.animateCamera(mCameraUpdate)
                                println(isExist)
                            }*/
                            if (isTrue) {//TODO
                                val builder = LatLngBounds.Builder()
                                for (markers in baseBusLocationArrayList) {
                                    val tempLocation = LatLng(markers.latitude, markers.longitude)
                                    builder.include(tempLocation)
                                }
                                val bounds = builder.build()
                                val padding = 10 // offset from edges of the map in pixels
                                val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                                mMap.animateCamera(cu)
                            }
                        }
                    }
                })
                handler.postDelayed(this, 15000)// this refers to runnable.

            }
        }
        isTrue = false
        isExist = false
        handler.post(runnable)
    }

    private fun getLastKnownLocation() {//TODO
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
                                this@BusLocation,
                                R.drawable.vector_user_location
                            )!!.toBitmap()
                        }
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .icon(fromBitmap(bitmap))
                            .title("Your Location")
                    )
                }
            }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable
            if (bitmapDrawable.bitmap != null) {
                return bitmapDrawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}