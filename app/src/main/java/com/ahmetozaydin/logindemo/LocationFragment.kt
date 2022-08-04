package com.ahmetozaydin.logindemo

import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmetozaydin.logindemo.databinding.FragmentLocationBinding



private lateinit var locationManager: LocationManager
private lateinit var locationListener: LocationListener
//private lateinit var mMap:GoogleMap
private lateinit var binding:FragmentLocationBinding//binding nasıl kullanılır fragmentlarda


class LocationFragment : Fragment() {

    /*private val callback = OnMapReadyCallback { googleMap ->
    mMap = googleMap




        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15f))
    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)*/
    }
}