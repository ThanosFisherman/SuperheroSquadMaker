package com.thanosfisherman.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.activities.HeroDetailsActivity
import com.thanosfisherman.presentation.adapters.HeroesRecyclerAdapter
import com.thanosfisherman.presentation.adapters.SquadRecyclerAdapter
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.MyDivider
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


@ExperimentalCoroutinesApi
@FlowPreview
class MainFragment : Fragment(R.layout.fragment_main) {
    private val HEROES_LIST_VIEW = 0
    private val HEROES_PROGRESS_VIEW = 1

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val heroesAdapter: HeroesRecyclerAdapter by lazy { HeroesRecyclerAdapter(lifecycleScope) }

    private val squadAdapter: SquadRecyclerAdapter by lazy { SquadRecyclerAdapter(lifecycleScope) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val layoutManagerVertical = LinearLayoutManager(requireContext())
        recycler_heroes.layoutManager = layoutManagerVertical
        recycler_heroes.addItemDecoration(MyDivider(requireContext()))
        recycler_heroes.adapter = heroesAdapter

        val layoutManagerHorizontal =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler_squad.setHasFixedSize(true)
        recycler_squad.layoutManager = layoutManagerHorizontal
        recycler_squad.adapter = squadAdapter

        heroesAdapter.itemClicks.debounce(100).onEach { startHeroDetailsActivity(it) }
            .launchIn(lifecycleScope)
        squadAdapter.itemClicksSquad.debounce(100).onEach { startHeroDetailsActivity(it) }
            .launchIn(lifecycleScope)

        val fadeIn = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_out)
        animator_load.inAnimation = fadeIn
        animator_load.outAnimation = fadeOut
        switchAnimatorView(HEROES_LIST_VIEW)
        lifecycleScope.launch {
            mainViewModel.liveCharactersPagedApi.collectLatest { heroesAdapter.submitData(it) }
        }

        //observe(mainViewModel.liveNetworkResult, ::onGetNetworkResultStateChange)
        observe(mainViewModel.liveSquadCharacters, ::onGetSquadStateChange)
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getSquad()
    }

    private fun onGetNetworkResultStateChange(networkResultState: NetworkResultState<List<CharacterModel>>) {
        when (networkResultState) {
            is NetworkResultState.Loading -> {
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
            else -> {
                Timber.i("ELSE STATE CALLED")
            }
        }
    }

    private fun startHeroDetailsActivity(characterModel: CharacterModel) {
        val intent = Intent(requireActivity(), HeroDetailsActivity::class.java)
        intent.putExtra("CharacterModel", characterModel)
        startActivity(intent)
    }

    private fun switchAnimatorView(id: Int) {
        if (animator_load.displayedChild != id) {
            animator_load.displayedChild = id
        }
    }
}
