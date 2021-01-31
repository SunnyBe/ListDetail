package com.buchi.listdetail.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.buchi.listdetail.MainApplication
import com.buchi.listdetail.R
import com.buchi.listdetail.utils.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as MainApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        processStateEvents()
    }

    private fun processStateEvents() {
        mainViewModel.  dataState.observe(this, { dataState ->
            dataState?.loading?.let { progress ->
                // Show Loading
//                showProgressDialog(progress)
            }

            dataState?.message?.let { err ->
                err.getContentIfNotHandled()?.let {
//                    showErrorDialog(it)
                }
            }

            dataState?.data?.getContentIfNotHandled()?.let { authViewState ->
                mainViewModel.setViewState(authViewState)
            }
        })
    }
}