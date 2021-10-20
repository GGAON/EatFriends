package com.zeronine.project1.widget.model

data class FoodListModel(
    val foodName : String,
    val price : Int
)
{
    constructor() : this("", 0)
}