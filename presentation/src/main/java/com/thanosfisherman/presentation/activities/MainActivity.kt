package com.thanosfisherman.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanosfisherman.domain.common.DbResultState
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.adapters.HeroesRecyclerAdapter
import com.thanosfisherman.presentation.common.extensions.observe
import com.thanosfisherman.presentation.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: HeroesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SquadMaker)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)
        adapter = HeroesRecyclerAdapter(lifecycleScope)
        val layoutManager = LinearLayoutManager(applicationContext)
        recycler_heroes.setHasFixedSize(true)
        recycler_heroes.layoutManager = layoutManager
        recycler_heroes.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))
        recycler_heroes.adapter = adapter
        adapter.itemClicks.debounce(300).onEach { characterModel ->
            val intent = Intent(this, HeroDetailsActivity::class.java)
            intent.putExtra("CharacterModel", characterModel)
            startActivity(intent)
        }.launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()
        /* observe(mainViewModel.heroesLive) { onGetHeroesStateChange(it) }
         observe(mainViewModel.squadLive) { onGetSquadStateChange(it) }*/
        observe(mainViewModel.observeRemotePagedSets()) { pagedList: PagedList<CharacterModel> -> adapter.submitList(pagedList) }
    }

    private fun onGetHeroesStateChange(dbResultState: DbResultState<List<CharacterModel>>?) {
        when (dbResultState) {
            is DbResultState.Loading -> Timber.i("LOADING.........")
            is DbResultState.Success -> {
                // adapter.submitList()
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
