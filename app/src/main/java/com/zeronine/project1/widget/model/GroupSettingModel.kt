package com.zeronine.project1.widget.model

data class GroupSettingModel(
    val groupSettingId: String,
    val recruiterId: String,
    val foodCategory: String,
    val totalPeople: Int,
    val waitingTime: Int,
    val recruiterLat: Double,
    val recruiterLng: Double,
    val recruiting: Int,
    val startTime: String
/*
        recruiting
        0 : 기본생성자 임의값
        1 : 공동구매 모집중
        2 : 공동구매 성공
        3 : 공동구매 실패
         */
)
{
    constructor(): this("", "", "", 0, 0, 37.55169195608614, 126.92498046225892, 0, "")
}