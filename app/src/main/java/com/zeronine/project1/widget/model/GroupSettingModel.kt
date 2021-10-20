package com.zeronine.project1.widget.model

data class GroupSettingModel(
    val groupSettingID: String,
    val recruiterId: String,
    val foodCategory: String,
    val totalPeople: String,
    val waitingTime: String,
    val recruiterLat: Double,
    val recruiterLng: Double,
    val recruiting: Int,
    val startTime: String

)
{
    constructor(): this("", "", "", "", "", 37.55169195608614, 126.92498046225892, 0, "")
}