package com.thanosfisherman.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.adapters.HeroesRecyclerAdapter
import com.thanosfisherman.presentation.adapters.SquadRecyclerAdapter
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val HEROES_LIST_VIEW = 0
    private val HEROES_PROGRESS_VIEW = 1

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var heroesAdapter: HeroesRecyclerAdapter
    private lateinit var squadAdapter: SquadRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SquadMaker)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        heroesAdapter = HeroesRecyclerAdapter(lifecycleScope)
        val layoutManagerVertical = LinearLayoutManager(applicationContext)
        recycler_heroes.setHasFixedSize(true)
        recycler_heroes.layoutManager = layoutManagerVertical
        recycler_heroes.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        recycler_heroes.adapter = heroesAdapter

        squadAdapter = SquadRecyclerAdapter(lifecycleScope)
        val layoutManagerHorizontal = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recycler_squad.setHasFixedSize(true)
        recycler_squad.layoutManager = layoutManagerHorizontal
        recycler_squad.adapter = squadAdapter

        heroesAdapter.itemClicks.debounce(100).onEach { startHeroDetailsActivity(it) }.launchIn(lifecycleScope)
        squadAdapter.itemClicksSquad.debounce(100).onEach { startHeroDetailsActivity(it) }.launchIn(lifecycleScope)

        val fadeIn = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
        animator_load.inAnimation = fadeIn
        animator_load.outAnimation = fadeOut

        observe(mainViewModel.liveCharactersPagedApi) { pagedList: PagedList<CharacterModel> -> heroesAdapter.submitList(pagedList) }
        observe(mainViewModel.liveNetworkResult, ::onGetNetworkResultStateChange)
        observe(mainViewModel.liveSquadCharacters, ::onGetSquadStateChange)
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getSquad()
    }

    private fun onGetNetworkResultStateChange(networkResultState: NetworkResultState<List<CharacterModel>>) {
        when (networkResultState) {
            is NetworkResultState.Loading -> {
                switchAnimatorView(HEROES_PROGRESS_VIEW)
            }
            is NetworkResultState.Success -> {
                switchAnimatorView(HEROES_LIST_VIEW)
            }
            is NetworkResultState.Error -> {
                RapidSnack.error(txtMySquad)
            }
        }
    }

    private fun onGetSquadStateChange(dbResultState: DbResultState<List<CharacterModel>>?) {
        when (dbResultState) {

            is DbResultState.Success -> {
                txtMySquad.visibility = View.VISIBLE
                recycler_squad.visibility = View.VISIBLE
                squadAdapter.submitList(dbResultState.data)
            }
            is DbResultState.EmptyError -> {
                txtMySquad.visibility = View.GONE
                recycler_squad.visibility = View.GONE
            }
            is DbResultState.GenericError -> {
                RapidSnack.error(txtMySquad)
            }
        }
    }

    private fun startHeroDetailsActivity(characterModel: CharacterModel) {
        val intent = Intent(this, HeroDetailsActivity::class.java)
        intent.putExtra("CharacterModel", characterModel)
        startActivity(intent)
    }

    private fun switchAnimatorView(id: Int) {
        if (animator_load.displayedChild != id) {
            animator_load.displayedChild = id
        }
    }
}
