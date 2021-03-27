package com.thanosfisherman.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@ExperimentalCoroutinesApi
class SquadRecyclerAdapter(private val coroutineScope: CoroutineScope) : ListAdapter<CharacterModel, SquadHolder>(HeroesDiffCallback()) {

    private val itemClicksChannel: Channel<CharacterModel> = Channel(Channel.UNLIMITED)

    @FlowPreview
    val itemClicksSquad: Flow<CharacterModel> = itemClicksChannel.receiveAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.squad_list_item, parent, false)
        return SquadHolder(view, itemClicksChannel, coroutineScope)
    }

    override fun onBindViewHolder(holder: SquadHolder, position: Int) {
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
