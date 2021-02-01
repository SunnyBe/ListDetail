package com.buchi.listdetail.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// All entity used in this module
sealed class MainEntity {

    data class ApiResponse<T>(
        val data: T?,
        val total: String?,
        val page: Int?,
        val limit: Int?,
        val offset: Int?

    ) : MainEntity()

//    @Entity
//    data class User(
//        @PrimaryKey
//        @ColumnInfo(name = "id")
//        val id: String,
//
//        @ColumnInfo(name = "firstName")
//        val firstName: String?,
//
//        @ColumnInfo(name = "lastName")
//        val lastName: String?,
//
//        @ColumnInfo(name = "title")
//        val title: String?,
//
//        @ColumnInfo(name = "email")
//        val email: String?,
//
//        @ColumnInfo(name = "picture")
//        val picture: String?
//    ) : MainEntity() {
//        companion object {
//            fun testUser(id: String = "user00") = User(
//                id = id,
//                email = "$id@test.com",
//                firstName = "Sandal",
//                lastName = "Mende",
//                title = "User Mende",
//                picture = "picture url"
//            )
//
//            fun listTestUser() = listOf(
//                testUser(),
//                testUser("user01"),
//                testUser("user02"),
//                testUser("user03"),
//                testUser("user04")
//            )
//        }
//    }

    @Entity
    data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: Int,

        @ColumnInfo(name = "name")
        val name: String?,

        @ColumnInfo(name = "username")
        val username: String?,

        @ColumnInfo(name = "email")
        val email: String?,

        @ColumnInfo(name = "picture")
        val phone: String?
    ) : MainEntity() {
        companion object {
            fun testUser(id: Int = 0) = User(
                id = id,
                email = "$id@test.com",
                name = "Sandal",
                username = "Mende",
                phone = "picture url"
            )

            fun listTestUser() = listOf(
                testUser(),
                testUser(1),
                testUser(2),
                testUser(3),
                testUser(4)
            )
        }
    }
}
