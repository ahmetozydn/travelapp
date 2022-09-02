package com.ahmetozaydin.logindemo.service

import com.ahmetozaydin.logindemo.model.Point
import com.ahmetozaydin.logindemo.model.ServiceModel
import com.ahmetozaydin.logindemo.model.Services
import retrofit2.Call
import retrofit2.http.GET

interface ServiceAPI {
    @GET("services")
    fun loadData() : Call<ServiceModel>
}