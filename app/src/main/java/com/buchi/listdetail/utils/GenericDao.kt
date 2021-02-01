package com.buchi.listdetail.utils

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert

@Dao
interface GenericDao<T> {

    fun getAll(): List<T>

    @Insert
    fun insertAll(vararg data: T)

    @Insert
    fun insert(data: T)

    @Delete
    fun delete(user: T)
}