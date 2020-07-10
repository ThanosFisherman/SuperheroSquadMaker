package com.thanosfisherman.presentation.activities

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.api.load
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.domain.model.ComicModel
import com.thanosfisherman.domain.model.ErrorModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.FragmentUtils
import com.thanosfisherman.presentation.common.utils.HideAppBarListener
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.fragments.AlertFragment
import com.thanosfisherman.presentation.fragments.FragmentInteractionListener
import com.thanosfisherman.presentation.viewmodels.HeroDetailsViewModel
import kotlinx.android.synthetic.main.activity_hero_details.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel
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

        fab.setOnClickListener { detailsViewModel.addOrRemoveFromSquad(characterModel) }

        characterModel?.let {

            observe(detailsViewModel.liveGetComicByCharId(it.id), ::getComicsState)
            observe(detailsViewModel.liveCheckInSquad, ::getCheckInSquadState)
            observe(detailsViewModel.liveAdRemove, ::getAddOrRemoveSquadState)

            txtDescription.text = if (it.description.isBlank()) getString(R.string.no_description) else it.description
            txtHeroName.text = it.name
            imgBackdrop.load(it.pic)
            detailsViewModel.checkIsInSquad(it)
            app_bar.addOnOffsetChangedListener(HideAppBarListener(it.name))

        }
    }

    private fun getCheckInSquadState(dbResultState: DbResultState<Boolean>) {
        when (dbResultState) {
            is DbResultState.Success -> {
                if (dbResultState.data) {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fire))
                    fab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.red_500))
                } else {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_heart))
                    fab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.colorAccent))
                }
            }
        }
    }

    private fun getAddOrRemoveSquadState(dbResultState: DbResultState<Boolean>) {
        when (dbResultState) {
            is DbResultState.Success -> {
                if (dbResultState.data) {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_fire))
                    fab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.red_500))
                    RapidSnack.success(fab, R.string.hero_added)
                } else {
                    fab.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_heart))
                    fab.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(applicationContext, R.color.colorAccent))
                    RapidSnack.fire(fab, R.string.hero_removed)
                }
            }
            is DbResultState.ShowDialodRemove -> {
                FragmentUtils.showDialog(
                    AlertFragment.newInstance(
                        getString(R.string.squad_alert_remove_title),
                        getString(R.string.squad_alert_remove_msg, characterModel?.name)
                    ),
                    supportFragmentManager
                )
            }
        }
    }

    private fun getComicsState(networkResultState: NetworkResultState<List<ComicModel>>) {

        when (networkResultState) {
            is NetworkResultState.Loading -> Timber.i("LOADING COMICS.........")
            is NetworkResultState.Success -> {
                populateComics(networkResultState.data)
            }
            is NetworkResultState.Error -> {
                when (val errorModel = networkResultState.error) {
                    is ErrorModel.Unknown -> {
                        Toast.makeText(applicationContext, errorModel.msg, Toast.LENGTH_LONG).show()
                    }
                    is ErrorModel.NetworkError -> Toast.makeText(applicationContext, R.string.network_error, Toast.LENGTH_LONG).show()
                    is ErrorModel.ServerError -> {
                        RapidSnack.error(fab, "SERVER ERROR ${errorModel.code} ${errorModel.message}")
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
    }

    private fun populateComics(comics: List<ComicModel>) {
        when {
            comics.isEmpty() -> txtLastAppeared.text = getString(R.string.no_appearances)
            comics.size == 1 -> {
                comic1.load(comics[0].pic)
            }
            comics.size == 2 -> {
                comic1.load(comics[0].pic)
                comic2.load(comics[1].pic)
            }
            comics.size > 2 -> {
                comic1.load(comics[0].pic)
                comic2.load(comics[1].pic)
                txtAndMore.text = getString(R.string.and_more, (comics.size - 2))
            }
        }
    }
}