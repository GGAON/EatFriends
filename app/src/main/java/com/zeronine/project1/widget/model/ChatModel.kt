package com.zeronine.project1.widget.model

class ChatModel(
    val users: HashMap<String, Boolean> = HashMap(),
    val comments: HashMap<String, Comment> = HashMap()
) {
    class Comment(val uid: String? = null, val message: String? = null, val time: String? = null)
}