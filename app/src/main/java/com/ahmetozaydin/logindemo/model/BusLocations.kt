package com.ahmetozaydin.logindemo.model

import com.google.gson.annotations.SerializedName

data class BusLocations(
    @SerializedName("vehicle_id")
    val vehicleID: String,
    @SerializedName("last_gps_fix")
    val lastGpsFix: Long,
    @SerializedName("last_gps_fix_secs")
    val lastGpsFixSecond: Long,
    val source: String? = null,
    val latitude: Double,
    val longitude: Double,
    val speed: Long? = null,
    val heading: Long? = null,
    @SerializedName("service_name")
    val serviceName: String? = null,
    val destination: String? = null,
    @SerializedName("journey_id")
    val journeyID: String? = null,
    @SerializedName("next_stop_id")
    val nextStopID: String? = null,
    @SerializedName("vehicle_type")
    val vehicleType: String? = null,
    @SerializedName("ineo_gps_fix")
    val ineoGpsFix: Long,
    @SerializedName("icomera_gps_fix")
    val icomeraGpsFix: Long? = null
)
