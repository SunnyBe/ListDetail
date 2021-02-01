package com.buchi.listdetail.utils

import androidx.room.TypeConverter
import com.buchi.listdetail.data.model.MainEntity
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun toLocation(locationJson: String): MainEntity.UserLocation? {
        return Gson().fromJson(locationJson, MainEntity.UserLocation::class.java)
    }

    @TypeConverter
    fun fromLocation(location: MainEntity.UserLocation?): String {
        return Gson().toJson(location)
    }
}