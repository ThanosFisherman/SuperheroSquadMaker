package com.thanosfisherman.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SquadMaker)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
    }

    override fun onStart() {
        super.onStart()
        observe(mainViewModel.getAllCharacters()) { useCaseResult ->
            when (useCaseResult) {
                is UseCaseResult.Success -> {
                    Timber.i(useCaseResult.data.toString())
                }
            }
        }
    }
}
