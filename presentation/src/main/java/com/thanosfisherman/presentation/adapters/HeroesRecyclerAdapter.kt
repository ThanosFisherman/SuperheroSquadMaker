package com.thanosfisherman.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.thanosfisherman.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

@ExperimentalCoroutinesApi
class HeroesRecyclerAdapter(private val coroutineScope: CoroutineScope) : ListAdapter<String, HeroHolder>(SonicoParamDiffCallback()) {

    private val itemClicksChannel: Channel<String> = Channel(Channel.UNLIMITED)

    @FlowPreview
    val itemClicks: Flow<String> = itemClicksChannel.consumeAsFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return HeroHolder(view, itemClicksChannel, coroutineScope)
    }

    override fun onBindViewHolder(holder: HeroHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    private class SonicoParamDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
