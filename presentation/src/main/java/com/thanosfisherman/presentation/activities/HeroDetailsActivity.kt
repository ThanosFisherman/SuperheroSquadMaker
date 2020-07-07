package com.thanosfisherman.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.viewmodels.HeroDetailsViewModel
import kotlinx.android.synthetic.main.activity_hero_details.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class HeroDetailsActivity : AppCompatActivity() {

    private val detailsViewModel: HeroDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)
        setSupportActionBar(detailsToolbar)
        toolbar_layout.title = title
        fab.setOnClickListener { RapidSnack.success(it) }
    }

    override fun onStart() {
        super.onStart()
        val characterModel = intent.getParcelableExtra<CharacterModel>("CharacterModel")
        characterModel?.let {
            txtDescription.text = it.description
            imgBackdrop.load(it.pic)
            observe(detailsViewModel.getComicByCharId(it.id), ::getComicsState)
        }

    }

    private fun getComicsState(networkResultState: NetworkResultState<List<ComicModel>>) {
        var comics = ""
        when (networkResultState) {
            is NetworkResultState.Loading -> Timber.i("LOADING COMICS.........")
            is NetworkResultState.Success -> {
                networkResultState.data.forEach {
                    comics = comics + "\n" + it.title
                    Timber.i("COMIC %s", it.title)
                }
                txtComics.text = comics
            }
            is NetworkResultState.Error -> {
                Timber.i("Comic update Error")
            }
        }
    }
}