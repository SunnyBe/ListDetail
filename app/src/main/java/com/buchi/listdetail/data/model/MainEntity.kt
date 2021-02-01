package com.buchi.listdetail.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// All entity used in this module
sealed class MainEntity {

    data class ApiListResponse(
        val data: List<User>?,
        val total: String?,
        val page: Int?,
        val limit: Int?,
        val offset: Int?

    ) : MainEntity()

    data class ApiResponse<T>(
        val data: T?,
        val total: String?,
        val page: Int?,
        val limit: Int?,
        val offset: Int?

    ) : MainEntity()

    @Entity
    data class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

        @ColumnInfo(name = "firstName")
        val firstName: String?,

        @ColumnInfo(name = "lastName")
        val lastName: String?,

        @ColumnInfo(name = "title")
        val title: String?,

        @ColumnInfo(name = "email")
        val email: String?,

        @ColumnInfo(name = "picture")
        val picture: String?,

        @ColumnInfo(name = "location")
        val location: UserLocation? = null,

        @ColumnInfo(name = "phone")
        val phone: String? = null,

        @ColumnInfo(name = "dob")
        val dateOfBirth: String? = null,

        @ColumnInfo(name = "gender")
        val gender: String? = null,

        @ColumnInfo(name = "regDate")
        val registerDate: String? = null,

        ) : MainEntity() {
        companion object {
            fun testUser(id: String = "user00") = User(
                id = id,
                email = "$id@test.com",
                firstName = "Sandal",
                lastName = "Mende",
                title = "User Mende",
                picture = "picture url"
            )

            fun listTestUser() = listOf(
                testUser(),
                testUser("user01"),
                testUser("user02"),
                testUser("user03"),
                testUser("user04")
            )
        }
    }

    data class UserLocation(
        @ColumnInfo(name = "state")
        val state: String? = null,

        @ColumnInfo(name = "timezone")
        val timezone: String? = null,

        @ColumnInfo(name = "street")
        val street: String? = null,

        @ColumnInfo(name = "city")
        val city: String? = null,

        @ColumnInfo(name = "country")
        val country: String? = null
    )
}
