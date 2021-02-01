package com.buchi.listdetail.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.buchi.listdetail.MainApplication
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.databinding.ActivityMainBinding
import com.buchi.listdetail.utils.MainViewModelFactory
import com.buchi.listdetail.utils.UserListAdapter

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as MainApplication).repository)
    }

    // Listener for UserListAdapter actions like item clicking
    private val listListener = object : UserListAdapter.UserListListener {
        override fun onItemClicked(item: MainEntity.User) {
            mainViewModel.fetchUserDetail(item.id)
        }
    }
    private val userListAdapter: UserListAdapter by lazy {
        UserListAdapter(listListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        processStateEvents()
        mainViewModel.fetchAllUsers()
    }

    private fun initViews() {
        binding.usersList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userListAdapter
        }

        binding.mainSwipeRefresh.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        mainViewModel.fetchAllUsers()
    }

    private fun processStateEvents() {
        mainViewModel.dataState.observe(this, { dataState ->
            dataState?.loading?.let { progress ->
                // Show Loading
                showProgressDialog(progress)
            }

            dataState?.message?.let { err ->
                err.getContentIfNotHandled()?.let {
                    showErrorDialog(it)
                }
            }

            dataState?.data?.getContentIfNotHandled()?.let { authViewState ->
                mainViewModel.setViewState(authViewState)
            }
        })

        mainViewModel.viewState.observe(this, { viewState ->
            viewState.allUser?.let { users ->
                Toast.makeText(this, "List was updated.", Toast.LENGTH_LONG).show()
                userListAdapter.submitList(users)
            }
            viewState.user?.let { user ->
                Toast.makeText(this, "User: $user", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showErrorDialog(errorMsg: String) {
        Toast.makeText(this, "Error: $errorMsg", Toast.LENGTH_LONG).show()
    }

    private fun showProgressDialog(showProgress: Boolean) {
        binding.mainSwipeRefresh.isRefreshing = showProgress
    }
}