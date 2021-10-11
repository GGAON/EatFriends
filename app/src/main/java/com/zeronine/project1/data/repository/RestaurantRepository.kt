package com.zeronine.project1.data.repository

import com.zeronine.project1.data.entity.RestaurantEntity

interface RestaurantRepository {

    suspend fun getList(): List<RestaurantEntity>
}