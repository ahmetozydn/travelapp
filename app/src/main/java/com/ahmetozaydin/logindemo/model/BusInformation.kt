package com.ahmetozaydin.logindemo.model

import com.google.android.gms.maps.model.Marker

data class BusInformation(
    val busId: String,
    var latitude: Double,
    var longitude: Double,
    val destination: String?,
    var marker : Marker
    )