package com.mertozan.geminidemo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GeminiViewModel : ViewModel() {

    private val _geminiState = MutableStateFlow(ChatUiState())
    val geminiState = _geminiState.asStateFlow()

    fun getAnswer() {
        viewModelScope.launch {
            _geminiState.value = _geminiState.value.copy(isLoading = true)
            val response = generativeModel.generateContent(_geminiState.value.question)
            _geminiState.value = _geminiState.value.copy(
                isLoading = false,
                answer = response.text.toString()
            )
        }
    }

    fun changeQuestion(question: String) {
        _geminiState.value = _geminiState.value.copy(question = question)
    }

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    data class ChatUiState(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val question: String = "",
        val answer: String = "Ask a question to Gemini AI!"
    )

}