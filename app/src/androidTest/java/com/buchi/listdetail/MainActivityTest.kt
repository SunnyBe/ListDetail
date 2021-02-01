package com.buchi.listdetail

import android.widget.ListAdapter
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.buchi.listdetail.presentation.MainActivity
import com.buchi.listdetail.utils.FileReader
import com.buchi.listdetail.utils.OkHttpProvider
import com.buchi.listdetail.utils.UserListAdapter
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
//    val activityRule = ActivityTestRule(MainActivity::class.java)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create("okhttp", OkHttpProvider.getOkHttpClient())
        )
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSuccessfulResponse() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFromFile("userlist_success_response.json"))
            }
        }
//        activityRule.launchActivity(null)
        activityRule.scenario.recreate()

        onView(withId(R.id.users_list))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.user_name))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    /**
     * Matches the [UserListAdapter.UserListViewHolder]s in the middle of the list.
     */
    private fun isInTheMiddle(): Matcher<UserListAdapter.UserListViewHolder?> {
        return object : TypeSafeMatcher<UserListAdapter.UserListViewHolder?>() {

            protected fun matchesSafely(customHolder: UserListAdapter.UserListListener): Boolean {
                return customHolder.getIsInTheMiddle()
            }

            override fun describeTo(description: Description) {
                description.appendText("item in the middle")
            }

            override fun matchesSafely(item: UserListAdapter.UserListViewHolder?): Boolean {
                return item
            }
        }
    }
}