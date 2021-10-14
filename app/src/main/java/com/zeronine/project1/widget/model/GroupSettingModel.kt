package com.zeronine.project1.widget.model

data class GroupSettingModel(
    val foodCategory: String,
    val groupSettingID: String,
    val recruiterId: String,
    val recruiterLat: String,
    val recruiterLng: String,
    val recruiting: String,
    val startTime: String ,
    val totalPeople: String,
    val waitingTime: String
)
{
    constructor(): this("", "", "", "", "", "", "", "", "")
}