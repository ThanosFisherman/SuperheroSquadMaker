package com.thanosfisherman.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.api.load
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.model.ErrorModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.FragmentUtils
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.fragments.AlertFragment
import com.thanosfisherman.presentation.fragments.FragmentInteractionListener
import com.thanosfisherman.presentation.viewmodels.HeroDetailsViewModel
import kotlinx.android.synthetic.main.activity_hero_details.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.view.clicks
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
class HeroDetailsActivity : AppCompatActivity(), FragmentInteractionListener {

    private val detailsViewModel: HeroDetailsViewModel by viewModel()
    private var characterModel: CharacterModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)
        setSupportActionBar(detailsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        characterModel = intent.getParcelableExtra("CharacterModel")
        toolbar_layout.title = characterModel?.name

        fab.clicks().debounce(300).onEach {
            detailsViewModel.addOrRemoveFromSquad(characterModel)
        }.launchIn(lifecycleScope)

        characterModel?.let {
            txtDescription.text = it.description
            imgBackdrop.load(it.pic)
            detailsViewModel.checkIsInSquad(it)
            observe(detailsViewModel.liveGetComicByCharId(it.id), ::getComicsState)
            observe(detailsViewModel.liveCheckInSquad) { dbResultState -> getCheckInSquadState(dbResultState) }
            observe(detailsViewModel.liveAdRemove) { dbResultState -> getAddOrRemoveSquadState(dbResultState) }
        }
    }

    private fun getCheckInSquadState(dbResultState: DbResultState<Boolean>) {
        when (dbResultState) {
            is DbResultState.Success -> {
                if (dbResultState.data) {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fire))
                } else {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_heart))
                }
            }
        }
    }

    private fun getAddOrRemoveSquadState(dbResultState: DbResultState<Boolean>) {
        when (dbResultState) {
            is DbResultState.Success -> {
                if (dbResultState.data) {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fire))
                    RapidSnack.success(fab)
                } else {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_heart))
                    RapidSnack.error(fab)
                }
            }
            is DbResultState.ShowDialodRemove -> {
                FragmentUtils.showDialog(AlertFragment.newInstance("DELETE?", "DELETE HERO?"), supportFragmentManager)
            }
        }
    }

    private fun getComicsState(networkResultState: NetworkResultState<List<ComicModel>>) {
        var comics = ""
        when (networkResultState) {
            is NetworkResultState.Loading -> Timber.i("LOADING COMICS.........")
            is NetworkResultState.Success -> {
                networkResultState.data.forEach {
                    comics = comics + "\n" + it.title
                }
                txtComics.text = comics
            }
            is NetworkResultState.Error -> {
                when (val errorModel = networkResultState.error) {
                    is ErrorModel.Unknown -> {
                        Timber.e(errorModel.msg)
                    }
                    is ErrorModel.NetworkError -> Timber.i("NETWORK ERROR")
                    is ErrorModel.ServerError -> {
                        Timber.i("SERVER ERROR " + errorModel.code + " " + errorModel.message)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun messageFromChildToParent() {
        detailsViewModel.addOrRemoveFromSquad(characterModel, true)
        Timber.i("MESSAGE FROM CHILD TO PARENT")
    }
}