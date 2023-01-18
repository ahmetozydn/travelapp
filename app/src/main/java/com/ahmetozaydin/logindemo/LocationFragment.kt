package com.ahmetozaydin.logindemo

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.ahmetozaydin.logindemo.databinding.FragmentLocationBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class LocationFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var binding: FragmentLocationBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
    private val callback = OnMapReadyCallback { googleMap ->//callback in yerini değiştirmiştim
        mMap = googleMap

    }

    override fun onResume() {
        super.onResume()
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getLastKnownLocation()
    }

    private fun getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    val userLocation = LatLng(location.latitude,location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
                    val bitmap =
                        context?.let { AppCompatResources.getDrawable(it,R.drawable.vector_user_location)!!.toBitmap() }
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
                    mMap.addMarker(
                        MarkerOptions()
                            .position(userLocation)
                            .alpha(0.9f)
                            .icon(bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) })
                            .title("Your Location"))
                }
            }
    }
}


