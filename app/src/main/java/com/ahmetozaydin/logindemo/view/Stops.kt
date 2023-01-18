package com.ahmetozaydin.logindemo.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.ahmetozaydin.logindemo.R
import com.ahmetozaydin.logindemo.adapter.InfoWindowAdapter
import com.ahmetozaydin.logindemo.databinding.ActivityStopsBinding
import com.ahmetozaydin.logindemo.model.Stop
import com.ahmetozaydin.logindemo.model.StopResponse
import com.ahmetozaydin.logindemo.service.BusAPI
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
import kotlin.math.cos
import kotlin.math.sin


class Stops : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStopsBinding
    private lateinit var location: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var bitmap: Bitmap
    private val markerList = ArrayList<Marker>()
    var stopList: ArrayList<Stop>? = null
    private var rectOptions = PolygonOptions()
    private lateinit var userLocation : LatLng

    companion object {
        const val BASE_URL = "https://tfe-opendata.com/api/v1/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        /* bitmap =
            baseContext.let { AppCompatResources.getDrawable(this@Stops,R.drawable.ic_stop)!!.toBitmap() }*/
        bitmap = drawableToBitmap(
            ContextCompat.getDrawable(this, R.drawable.vector_bus)!!
        )


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val markerTurkey = arrayListOf<LatLng>()
        var aLtLng = LatLng(55.91095709328989, -3.5586319675846534)
        markerTurkey.add(aLtLng)
        aLtLng = LatLng(55.92037357728969, -2.9104026111621213)
        markerTurkey.add(aLtLng)
        val builder1 = LatLngBounds.builder()
        markerTurkey.forEach {
            builder1.include(it)
        }
        val bounds2 = builder1.build()
        val cameraUpdateFactory =  CameraUpdateFactory.newLatLngBounds(bounds2, 100)
        mMap.moveCamera(cameraUpdateFactory)
        mMap.setInfoWindowAdapter(InfoWindowAdapter(this))
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
                            location = LatLng(stop.latitude!!, stop.longitude!!)
                          /*  val results = FloatArray(1)
                            Location.distanceBetween(userLocation.latitude,userLocation.longitude,location.latitude,location.longitude,results)
                            val realDistance = distance(userLocation.latitude.toFloat(),userLocation.longitude.toFloat(),location.latitude.toFloat(),location.longitude.toFloat())
                            println(realDistance)
                            results.forEach {
                                println("distance : $it")
                            }*/
                            val marker = mMap.addMarker(
                                MarkerOptions()
                                    .alpha(0.8f)
                                    .position(location)
                                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                                    .title(stop.stop_id.toString())
                                    .snippet("Bus Id")
                            )
                            if (marker != null) {
                                markerList.add(marker)
                            }
                            counter++
                            if (counter == 10) {
                                break
                            }
                        }
                        val builder = LatLngBounds.builder()
                        markerList.forEach {
                            builder.include(it.position)
                        }
                        val bounds = builder.build()
                        val mCameraUpdateFactory = CameraUpdateFactory.newLatLngBounds(bounds, 100)
                        mMap.animateCamera(mCameraUpdateFactory)
                    }
                }
            }
        })
        getLastKnownLocation()
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
                    // use your location object
                    // get latitude , longitude and other info from this
                    userLocation = LatLng(location.latitude, location.longitude)
                    val bitmap =
                        baseContext.let {
                            AppCompatResources.getDrawable(
                                this@Stops,
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
    fun distance(
        lat1: Float,
        lng1: Float,
        lat2: Float,
        lng2: Float
    ): Float {
        val earthRadius = 6371000.0 //meters
        val dLat = Math.toRadians((lat2 - lat1).toDouble())
        val dLng = Math.toRadians((lng2 - lng1).toDouble())
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1.toDouble())) * cos(
            Math.toRadians(lat2.toDouble())
        ) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c =
            2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return (earthRadius * c).toFloat()
    }


}