package com.thanosfisherman.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterModel(val id: Long, val name: String, val description: String, val pic: String) : Parcelable