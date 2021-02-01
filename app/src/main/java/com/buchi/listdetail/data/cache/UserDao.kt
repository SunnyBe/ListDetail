package com.buchi.listdetail.data.cache

import androidx.room.*
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.utils.GenericDao

@Dao
interface UserDao : GenericDao<MainEntity.User> {

    @Query("SELECT * FROM user")
    override fun getAll(): List<MainEntity.User>

    @Query("SELECT * FROM user WHERE :firstName == firstName")
    fun findByFirstName(firstName: String): List<MainEntity.User>

    @Query("SELECT * FROM user WHERE :email == lastName")
    fun findByEmail(email: String): List<MainEntity.User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertAll(vararg data: MainEntity.User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(data: MainEntity.User)

    @Delete
    override fun delete(user: MainEntity.User)
}
