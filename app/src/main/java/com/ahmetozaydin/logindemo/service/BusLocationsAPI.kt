package com.ahmetozaydin.logindemo.service

import android.telecom.Call
import com.ahmetozaydin.logindemo.model.BusLocationModel
import retrofit2.http.GET

interface BusLocationsAPI {
        @GET("vehicle_loations")
        fun getData() : Call<BusLocationModel>

}