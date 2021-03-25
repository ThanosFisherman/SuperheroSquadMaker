package com.thanosfisherman.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

@ExperimentalCoroutinesApi
class HeroesRecyclerAdapter(private val coroutineScope: CoroutineScope) : PagingDataAdapter<CharacterModel, HeroHolder>(HeroesDiffCallback()) {

    private val itemClicksChannel: Channel<CharacterModel> = Channel(Channel.UNLIMITED)

    @FlowPreview
    val itemClicks: Flow<CharacterModel> = itemClicksChannel.consumeAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return HeroHolder(view, itemClicksChannel, coroutineScope)
    }

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        getItem(position)?.let { holder.bindItem(it) }
    }

    private class HeroesDiffCallback : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }

    }
}
