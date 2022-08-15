package com.ahmetozaydin.logindemo.model

import com.google.gson.annotations.SerializedName

data class ServiceModel(
    @SerializedName("last_update")
    val lastUpdated: Long,
    val services: List<Services>//list of Services Class
)
data class Services(
    val name: String? ,
    val description: String? ,
    @SerializedName("service_type")
    val serviceType: String? ,
    val routes: List<Route>?
)
data class Route (
    val destination: String? ,
    val points: List<Point>? ,
    val stops: List<String>?
)
data class Point (
    @SerializedName("stop_id")
    val stopID: String?,
    val latitude: Double,
    val longitude: Double
)
