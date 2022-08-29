package com.ahmetozaydin.logindemo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StopResponse (
    @SerializedName("last_updated")
    val lastUpdated: Long,
    @SerializedName("stops")
    @Expose
    val stops: ArrayList<Stop>
)


