package com.thanosfisherman.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thanosfisherman.domain.model.CharacterModel
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


class HeroHolder(
    itemView: View,
    private val clicksChannel: Channel<CharacterModel>,
    private val coroutineScope: CoroutineScope
) : RecyclerView.ViewHolder(itemView) {

    @ExperimentalCoroutinesApi
    fun bindItem(hero: CharacterModel) {
        itemView.clicks().onEach { clicksChannel.offer(hero) }.launchIn(coroutineScope)
        itemView.heroName.text = hero.name
        itemView.avatar.load(hero.pic)
    }
}