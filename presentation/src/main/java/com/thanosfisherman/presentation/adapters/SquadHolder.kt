package com.thanosfisherman.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import kotlinx.android.synthetic.main.squad_list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks


class SquadHolder(
    itemView: View,
    private val clicksChannel: Channel<CharacterModel>,
    private val coroutineScope: CoroutineScope
) : RecyclerView.ViewHolder(itemView) {

    @ExperimentalCoroutinesApi
    fun bindItem(hero: CharacterModel) {
        itemView.clicks().onEach { clicksChannel.offer(hero) }.launchIn(coroutineScope)
        itemView.squadAvatar.load(hero.pic) {
            crossfade(false)
            placeholder(R.drawable.image_not_available)
            error(R.drawable.image_not_available)
            fallback(R.drawable.image_not_available)
            transformations(CircleCropTransformation())
        }
    }
}