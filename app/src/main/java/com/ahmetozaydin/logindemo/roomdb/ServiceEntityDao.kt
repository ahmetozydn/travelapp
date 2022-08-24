package com.ahmetozaydin.logindemo.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ahmetozaydin.logindemo.model.Point
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ServiceEntityDao {
    @Query("SELECT * FROM ServiceEntity")
    fun getAll():Flowable<List<ServiceEntity>>
    @Insert
    fun insert(serviceEntity: ServiceEntity) : Completable
    @Delete
    fun delete(ServiceEntity: ServiceEntity) : Completable

}