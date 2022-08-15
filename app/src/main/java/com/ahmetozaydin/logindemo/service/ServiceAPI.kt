package com.ahmetozaydin.logindemo.service

import com.ahmetozaydin.logindemo.model.Point
import com.ahmetozaydin.logindemo.model.ServiceModel
import retrofit2.Call
import retrofit2.http.GET

interface ServiceAPI {
    @GET("services")
    fun getData() : Call<ServiceModel>
}