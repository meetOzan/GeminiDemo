package com.mertozan.geminidemo.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.mertozan.geminidemo.common.ChatType
import com.mertozan.geminidemo.di.GeminiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @GeminiModule.GeminiPro private val provideGeminiModel: GenerativeModel
)  : ViewModel() {

    private val _chatState = MutableStateFlow(ChatUiState())
    val chatState = _chatState.asStateFlow()


    fun getAnswer() {
        viewModelScope.launch {
            _chatState.value = _chatState.value.copy(isLoading = true)
            val response = provideGeminiModel.generateContent(_chatState.value.question)
            _chatState.value = _chatState.value.copy(
                isLoading = false,
                question = "",
                answer = response.text.toString()
            )
            addChatItem(ChatItem(ChatType.AI, response.text.toString()))
        }
    }

    fun changeQuestion(question: String) {
        _chatState.value = _chatState.value.copy(question = question)
    }

    fun addChatItem(chatItem: ChatItem) {
        _chatState.value = _chatState.value.copy(
            chatList = _chatState.value.chatList + chatItem
        )
    }

}

data class ChatUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val question: String = "",
    val answer: String = "Ask a question to Gemini AI!",
    val chatList: List<ChatItem> = emptyList()
)