package com.ahmetozaydin.logindemo.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ServiceEntity(
                         @ColumnInfo(name = "name")
                         var name : String?,
                         @ColumnInfo(name = "description")
                         var description : String?,)
{
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
