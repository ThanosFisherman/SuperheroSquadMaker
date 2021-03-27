package com.thanosfisherman.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thanosfisherman.presentation.R
import kotlinx.android.synthetic.main.footer_view_item.view.*
import timber.log.Timber

class HeroesLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<HeroesLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: HeroesLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): HeroesLoadStateViewHolder {
        return HeroesLoadStateViewHolder.from(parent, retry)
    }
}

class HeroesLoadStateViewHolder private constructor(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.retry_button?.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            itemView.error_msg.text = loadState.error.localizedMessage
        }
        Timber.i("BINDING FOOTER HEADER $loadState")
        itemView.footer_progress_bar.isVisible = loadState is LoadState.Loading
        itemView.retry_button.isVisible = loadState !is LoadState.Loading
        itemView.error_msg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): HeroesLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val retryView = inflater.inflate(R.layout.footer_view_item, parent, false)
            return HeroesLoadStateViewHolder(retryView, retry)
        }
    }
}

