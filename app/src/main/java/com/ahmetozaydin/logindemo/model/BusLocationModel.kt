package com.ahmetozaydin.logindemo.model

data class BusLocationModel(
    val last_updated: Long,
    val vehicles: List<BusLocations>
)

data class BusLocations(
    val vehicle_id: String,
    val last_gps_fix: Long,
    val last_gps_fix_second: Long,
    val source: String? = null,
    val latitude: Double,
    val longitude: Double,
    val speed: Long? = null,
    val heading: Long? = null,
    val service_ame: String? = null,
    val destination: String? = null,
    val journey_id: String? = null,
    val next_stop_id: String? = null,
    val vehicle_type: String? = null,
    val ineo_gps_fix: Long,
    val icomera_gps_fix: Long? = null
)

