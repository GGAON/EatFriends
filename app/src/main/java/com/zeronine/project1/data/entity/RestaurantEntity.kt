package com.zeronine.project1.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RestaurantEntity(
    override val id:Long,
    val restaurantInfoId: Long,
    val restaurantTitle: String
) : Entity, Parcelable
