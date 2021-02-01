package com.buchi.listdetail.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.buchi.listdetail.MainApplication
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.databinding.ActivityMainBinding
import com.buchi.listdetail.databinding.DialogDetailBinding
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

    // Observe and process dataState and viewState liveData/event updates
    private fun processStateEvents() {
        mainViewModel.dataState.observe(this, { dataState ->
            dataState?.loading?.let { progress ->
                // Show Loading
                showProgressDialog(progress)
            }

            dataState?.message?.let { err ->
                // Ignore error if dataState has data. Data state displays msg with data as a Toast to give users context.
                if (dataState.data == null) {
                    err.getContentIfNotHandled()?.let { msg ->
                        showErrorDialog(errTitle = "Failed", errorMsg = msg)
                    }
                }
            }

            dataState?.data?.getContentIfNotHandled()?.let { authViewState ->
                // If data comes with a msg
                if (dataState.data != null && dataState.message != null) {
                    Toast.makeText(this, "List was updated.", Toast.LENGTH_LONG).show()
                }
                mainViewModel.setViewState(authViewState)
            }
        })

        mainViewModel.viewState.observe(this, { viewState ->
            viewState.allUser?.let { users ->
                userListAdapter.submitList(users)
            }
            viewState.user?.let { user ->
                detailDialog(user)
            }
        })
    }

    private fun showErrorDialog(errTitle: String?= "Failed", errorMsg: String) {
        val errorDialogBuilder = AlertDialog.Builder(this).apply {
            title = errTitle
            setMessage(errorMsg)
            setPositiveButton("Ok") { dialog, p ->
                dialog.dismiss()
            }
            setOnCancelListener { dialog ->
                dialog.dismiss()
            }
        }
        errorDialogBuilder.setCancelable(false)
        errorDialogBuilder.show()
    }

    private fun detailDialog(user: MainEntity.User) {
        val detailBinding = DialogDetailBinding.inflate(layoutInflater)
        val detailView = detailBinding.root
        val detailBuilder = AlertDialog.Builder(this).setView(detailView)
        detailBuilder.setCancelable(false)
        detailBinding.userDetailHeading.text = user.username
        detailBinding.userNameValue.text = user.name
        detailBinding.emailValue.text = user.email
        detailBinding.userPhoneValue.text = user.phone
        val detailDialog = detailBuilder.show()
        detailBinding.cancelAction.setOnClickListener {
            detailDialog.dismiss()
        }
    }

    private fun showProgressDialog(showProgress: Boolean) {
        binding.mainSwipeRefresh.isRefreshing = showProgress
    }
}