package com.thanosfisherman.presentation.common.utils

import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_hero_details.view.*

class HideAppBarListener(private val title: String) : AppBarLayout.OnOffsetChangedListener {


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if ((Math.abs(verticalOffset) - appBarLayout!!.totalScrollRange) == 0) {
            //collapsed
            appBarLayout.toolbar_layout.title = title
        } else {
            appBarLayout.toolbar_layout.title = ""
        }
    }
}