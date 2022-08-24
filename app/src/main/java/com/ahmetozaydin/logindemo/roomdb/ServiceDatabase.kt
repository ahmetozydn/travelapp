package com.ahmetozaydin.logindemo.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahmetozaydin.logindemo.model.Point

@Database(entities = [ServiceEntity::class], version = 1)
abstract class ServiceDatabase : RoomDatabase() {
    abstract fun serviceEntityDao(): ServiceEntityDao
}

