package com.buchi.listdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.data.repository.MainRepository
import com.buchi.listdetail.data.repository.MainRepositoryImpl
import com.buchi.listdetail.presentation.MainViewModel
import com.buchi.listdetail.presentation.MainViewState
import com.buchi.listdetail.utils.MainCoroutineScopeRule
import com.buchi.listdetail.utils.ResultState
import com.buchi.listdetail.utils.getValueForTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Mock
    lateinit var mainRepo: MainRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(mainRepo)
    }

    @After
    fun cleanUp() {

    }

    @Test
    fun fetchUserList_Successfully_updates_dataState_withEmptyList() {
        coroutineScope.runBlockingTest {
            val testFlow = flow {
                emit(ResultState.data(null, MainViewState(allUser = listOf())))
            }
            // Process
            viewModel.fetchAllUsers()
            Mockito.`when`(mainRepo.allList()).thenReturn(testFlow)
            // Test
            val expected = listOf<MainEntity.User>()
            Assert.assertEquals(expected, viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.allUser)
        }
    }
    @Test
    fun fetchUserList_Successfully_updates_dataState_withListValidFirstItem() {
        coroutineScope.runBlockingTest {
            val testFlow = flow {
                emit(ResultState.data(null, MainViewState(allUser = MainEntity.User.listTestUser())))
            }
            // Process
            viewModel.fetchAllUsers()
            Mockito.`when`(mainRepo.allList()).thenReturn(testFlow)
            // Test
            val expectedFirstItemId = 0
            Assert.assertEquals(expectedFirstItemId, viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.allUser?.first()?.id)
        }
    }

    @Test
    fun fetchUserList_Successfully_updates_dataState_withListValidLastItem() {
        coroutineScope.runBlockingTest {
            val testFlow = flow {
                emit(ResultState.data(null, MainViewState(allUser = MainEntity.User.listTestUser())))
            }
            // Process
            viewModel.fetchAllUsers()
            Mockito.`when`(mainRepo.allList()).thenReturn(testFlow)
            // Test
            val expectedLastItemId = 4
            Assert.assertEquals(expectedLastItemId, viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.allUser?.last()?.id)
        }
    }


    @Test(expected = Throwable::class)
    fun fetchUserList_Failedto_updates_dataState_withError() {
        // Process
        viewModel.fetchAllUsers()
        Mockito.`when`(mainRepo.allList()).thenThrow(Throwable("A test issue occurred"))
        // Test
        val expectedErrorMsg = "A test issue occurred"
        Assert.assertEquals(expectedErrorMsg, (viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.allUser as Throwable).message)
    }

    @Test
    fun fetchUserDetail_Successfully_updates_dataState_withValidUserDetail() {
        coroutineScope.runBlockingTest {
            val testFlow = flow {
                emit(ResultState.data(null, MainViewState(user = MainEntity.User.testUser(3))))
            }
            // Process
            viewModel.fetchUserDetail(3)
            Mockito.`when`(mainRepo.userDetail(3)).thenReturn(testFlow)
            // Test
            val expectedEmail = "3@test.com"
            Assert.assertEquals(expectedEmail, viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.user?.email)
        }
    }


    @Test(expected = Throwable::class)
    fun fetchUserDetail_Failedto_updates_dataState_withError() {
        // Process
        viewModel.fetchUserDetail(3)
        Mockito.`when`(mainRepo.userDetail(3)).thenThrow(Throwable("A test issue occurred"))
        // Test
        val expectedErrorMsg = "A test issue occurred"
        Assert.assertEquals(expectedErrorMsg, (viewModel.dataState.getValueForTest()?.data?.getContentIfNotHandled()?.allUser as Throwable).message)
    }
}