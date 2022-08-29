package com.ahmetozaydin.logindemo.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class ServiceModel(
    @SerializedName("last_update")
    val lastUpdated: Long,
    @SerializedName("services")
    @Expose
    val services: List<Services>?//list of Services Class
) : Parcelable
@Parcelize
data class Services(
    @SerializedName("name")
    val name: String? ,
    @SerializedName("description")
    val description: String? ,
    @SerializedName("service_type")
    val serviceType: String? ,
    @SerializedName("routes")
    val routes: List<Route>?
) : Parcelable

@Parcelize
data class Route (
    @SerializedName("destination")
    val destination: String? ,
    @SerializedName("points")
    val points: List<Point>? ,
    @SerializedName("stops")
    val stops: List<String>?
) : Parcelable
@Parcelize
data class Point (
    @SerializedName("stop_id")
    val stopID: String?,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
): Parcelable
