package com.ahmetozaydin.logindemo.service

import com.ahmetozaydin.logindemo.model.BusLocationModel
import com.ahmetozaydin.logindemo.model.BusLocations
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.PUT

interface BusLocationsAPI {
        @GET("vehicle_locations")
        fun getData() : Call<BusLocationModel>
}