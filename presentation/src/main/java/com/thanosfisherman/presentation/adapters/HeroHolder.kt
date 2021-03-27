package com.thanosfisherman.presentation.adapters

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.thanosfisherman.domain.model.CharacterModel
import com.thanosfisherman.presentation.R
import com.thanosfisherman.presentation.activities.HeroDetailsActivity
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
        itemView.avatar.load(hero.pic) {
            crossfade(false)
            placeholder(R.drawable.splash_logo)
            error(R.drawable.image_not_available)
            fallback(R.drawable.image_not_available)
            transformations(CircleCropTransformation())
        }
    }

    private fun startHeroDetailsActivity(characterModel: CharacterModel, imageView: ImageView) {
        val intent = Intent(itemView.context, HeroDetailsActivity::class.java)
        intent.putExtra("CharacterModel", characterModel)
        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="hero"
        val options = ActivityOptions
            .makeSceneTransitionAnimation(itemView.context as Activity, imageView, "hero")
        itemView.context.startActivity(intent, options.toBundle())

    }
}