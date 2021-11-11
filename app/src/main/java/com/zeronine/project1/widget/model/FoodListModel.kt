package com.zeronine.project1.widget.model

data class FoodListModel(
    val foodId : String,
    val foodName : String,
    val foodDetail : String,
    val foodPrice : Int
)
{
    constructor() : this("", "","", 0)
}