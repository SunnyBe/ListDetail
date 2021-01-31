package com.buchi.listdetail.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.utils.GenericDao

@Dao
interface UserDao : GenericDao<MainEntity.User> {

    @Query("SELECT * FROM user")
    override fun getAll(): List<MainEntity.User>

    @Insert
    override fun insertAll(vararg data: MainEntity.User)

    @Delete
    override fun delete(user: MainEntity.User)
}
