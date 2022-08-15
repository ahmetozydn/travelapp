package com.ahmetozaydin.logindemo.service

import com.ahmetozaydin.logindemo.model.BusLocationModel
import com.ahmetozaydin.logindemo.model.ServiceModel
import com.ahmetozaydin.logindemo.model.StopResponse
import retrofit2.Call
import retrofit2.http.GET

interface BusAPI {
    @GET("stops")
    fun getData() : Call<StopResponse>
}
