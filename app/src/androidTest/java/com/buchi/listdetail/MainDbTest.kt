package com.buchi.listdetail

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.buchi.listdetail.data.cache.MainDatabase
import com.buchi.listdetail.data.cache.UserDao
import com.buchi.listdetail.data.model.MainEntity
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MainDbTest {
    private lateinit var userDao: UserDao
    private lateinit var db: MainDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MainDatabase::class.java).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user: MainEntity.User = MainEntity.User.testUser(3)
        userDao.insert(user)
        val byName = userDao.findByUserName("Mende")
        assertThat(byName[0], equalTo(user))
    }

    @Test
    @Throws(Exception::class)
    fun writeUserListAndReadInList() {
        val users: List<MainEntity.User> = MainEntity.User.listTestUser()
        userDao.insertAll(*users.toTypedArray())
        val byName = userDao.findByUserName("Mende")
        assertThat(byName[0], equalTo(users.first()))
    }
}