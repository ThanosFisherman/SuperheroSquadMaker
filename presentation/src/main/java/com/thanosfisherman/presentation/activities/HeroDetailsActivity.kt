package com.thanosfisherman.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks
import timber.log.Timber

@FlowPreview
class HeroDetailsActivity : AppCompatActivity() {

    private val detailsViewModel: HeroDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)
        setSupportActionBar(detailsToolbar)
        toolbar_layout.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        fab.clicks().debounce(300).onEach {
            RapidSnack.success(fab)
        }.launchIn(lifecycleScope)

        val characterModel = intent.getParcelableExtra<CharacterModel>("CharacterModel")
        characterModel?.let {
            txtDescription.text = it.description
            imgBackdrop.load(it.pic)
            observe(detailsViewModel.liveGetComicByCharId(it.id), ::getComicsState)
        }
    }

    override fun onStart() {
        super.onStart()

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}