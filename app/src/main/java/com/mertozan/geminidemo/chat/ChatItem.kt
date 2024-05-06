package com.mertozan.geminidemo.chat

import com.mertozan.geminidemo.common.ChatType

data class ChatItem(
    val chatType: ChatType,
    val text: String
)
