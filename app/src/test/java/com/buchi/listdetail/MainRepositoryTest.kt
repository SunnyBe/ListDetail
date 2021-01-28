package com.buchi.listdetail

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

class MainRepositoryTest {

    private val mockWebServer = MockWebServer()
    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}