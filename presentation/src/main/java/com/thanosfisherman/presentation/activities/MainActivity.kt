package com.thanosfisherman.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@ExperimentalCoroutinesApi
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
        observe(mainViewModel.heroesLive) { onGetHeroesStateChange(it) }
        observe(mainViewModel.squadLive) { onGetSquadStateChange(it) }
    }

    private fun onGetHeroesStateChange(dbResultState: DbResultState<List<CharacterModel>>?) {
        when (dbResultState) {
            is DbResultState.Loading -> Timber.i("LOADING.........")
            is DbResultState.Success -> {
                Timber.i(dbResultState.data[1].name)
            }
            is DbResultState.EmptyError -> {
                Timber.i("EmptyError")
            }
            is DbResultState.GenericError -> {
                Timber.i("GenericError")
            }
        }
    }

    private fun onGetSquadStateChange(dbResultState: DbResultState<List<CharacterModel>>?) {
        when (dbResultState) {
            is DbResultState.Loading -> Timber.i("LOADING SQUAAAAAADDD.........")
            is DbResultState.Success -> {
                Timber.i("GOT SQUAD")
                Timber.i(dbResultState.data[1].name)
            }
            is DbResultState.EmptyError -> {
                Timber.i("EmptyError SQUAD")
            }
            is DbResultState.GenericError -> {
                Timber.i("GenericError SQUAD")
            }
        }
    }
}
