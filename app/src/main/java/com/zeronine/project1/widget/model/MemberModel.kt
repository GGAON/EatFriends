package com.zeronine.project1.widget.model

data class MemberModel(
    val MemberId: String,
    val MemberName: String
)
{
    constructor():this("", "")
}
