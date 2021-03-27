package com.thanosfisherman.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.common.NetworkResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.activities.HeroDetailsActivity
import com.thanosfisherman.presentation.adapters.HeroesLoadStateAdapter
import com.thanosfisherman.presentation.adapters.HeroesRecyclerAdapter
import com.thanosfisherman.presentation.adapters.SquadRecyclerAdapter
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.common.utils.MyDivider
import com.thanosfisherman.presentation.common.utils.RapidSnack
import com.thanosfisherman.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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
    var loadsCounter: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fadeIn = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(requireContext(), android.R.anim.fade_out)

        animator_load.inAnimation = fadeIn
        animator_load.outAnimation = fadeOut
        animator_load.switchAnimatorView(HEROES_LIST_VIEW)

        setupRecyclerView()
        initAdapters()
        setupObservers()
    }

    private fun setupObservers() {
        heroesAdapter.itemClicks.debounce(200).onEach { startHeroDetailsActivity(it) }
            .launchIn(lifecycleScope)
        squadAdapter.itemClicksSquad.debounce(200).onEach { startHeroDetailsActivity(it) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            mainViewModel.liveCharactersPagedApi.collectLatest { heroesAdapter.submitData(it) }
        }

        //observe(mainViewModel.liveNetworkResult, ::onGetNetworkResultStateChange)
        observe(mainViewModel.liveSquadCharacters, ::onGetSquadStateChange)
    }

    private fun initAdapters() {
        recycler_squad.adapter = squadAdapter
        scrollToTopWhenNewResultsArrive()
        heroesAdapter.addLoadStateListener { updateUiOnNewLoadSate(it) }
        val heroesConcat = heroesAdapter.withLoadStateHeaderAndFooter(
            footer = HeroesLoadStateAdapter { heroesAdapter.retry() },
            header = HeroesLoadStateAdapter { heroesAdapter.retry() }
        )
        recycler_heroes.adapter = heroesConcat
    }

    private fun setupRecyclerView() {
        val layoutManagerVertical = LinearLayoutManager(requireContext())
        recycler_heroes.layoutManager = layoutManagerVertical
        recycler_heroes.addItemDecoration(MyDivider(requireContext()))

        val layoutManagerHorizontal =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recycler_squad.layoutManager = layoutManagerHorizontal
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
                animator_load.switchAnimatorView(HEROES_LIST_VIEW)
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

    private fun scrollToTopWhenNewResultsArrive() {
        lifecycleScope.launch {
            heroesAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recycler_heroes.scrollToPosition(0) }
        }
    }

    private fun updateUiOnNewLoadSate(loadState: CombinedLoadStates) {

        if (loadState.source.refresh is LoadState.Loading)
            animator_load.switchAnimatorView(HEROES_PROGRESS_VIEW)
        else
            animator_load.switchAnimatorView(HEROES_LIST_VIEW)

        // Show empty results if results are less or equal 10
        if (loadState.source.refresh is LoadState.NotLoading && heroesAdapter.itemCount <= 0 && loadsCounter > 1) {
            Snackbar.make(requireView(), "NO RESULTS FOUND", Snackbar.LENGTH_LONG)
        }
        if (loadsCounter < 1)
            loadsCounter++

        // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error
            ?: loadState.refresh as? LoadState.Error
        errorState?.let {
            Toast.makeText(
                requireContext(),
                "\uD83D\uDE28 Wooops ${it.error}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun startHeroDetailsActivity(characterModel: CharacterModel) {
        val intent = Intent(requireActivity(), HeroDetailsActivity::class.java)
        intent.putExtra("CharacterModel", characterModel)
        startActivity(intent)
    }

    private fun ViewAnimator.switchAnimatorView(id: Int) {
        if (this.displayedChild != id) {
            this.displayedChild = id
        }
    }
}
