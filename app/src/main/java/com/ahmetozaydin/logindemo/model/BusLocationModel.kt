package com.ahmetozaydin.logindemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusLocationModel(
    @SerializedName("last_update")
    val lastUpdate: Long,
    @SerializedName("vehicles")
    @Expose
    val vehicles: List<BusLocations>
)



