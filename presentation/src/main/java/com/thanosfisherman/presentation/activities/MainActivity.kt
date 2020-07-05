package com.thanosfisherman.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thanosfisherman.domain.common.UseCaseResult
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ErrorModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.RapidSnack
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
        observe(mainViewModel.getAllCharacters(), ::onGetHeroesStateChange)
    }

    private fun onGetHeroesStateChange(useCaseResult: UseCaseResult<List<CharacterModel>>) {
        when (useCaseResult) {
            is UseCaseResult.Loading -> Timber.i("LOADINGGGGG")
            is UseCaseResult.Success -> {
                Timber.i(useCaseResult.data.toString())
            }
            is UseCaseResult.Error -> {
                when (useCaseResult.error) {
                    is ErrorModel.NetworkError -> {
                        Timber.i("NETWORK ERROR PLS TRY AGAIN LATER")
                    }
                    is ErrorModel.ServerError -> {
                        val msg = (useCaseResult.error as ErrorModel.ServerError).message ?: "ERROR Try again later"
                        RapidSnack.error(mainToolbar, msg)
                    }
                }
            }
        }
    }
}
