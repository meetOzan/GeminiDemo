package com.mertozan.geminidemo

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ImageViewModel : ViewModel() {

    private val _imageState = MutableStateFlow(ImageUiState())
    val imageState get() = _imageState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    fun askWithImage() {
        viewModelScope.launch {

            val inputContent = content {
                image(_imageState.value.image)
                text(_imageState.value.text)
            }

            _imageState.value = _imageState.value.copy(isLoading = true)
            val response = generativeModel.generateContent(inputContent)
            _imageState.value = _imageState.value.copy(
                isError = false,
                isLoading = false,
                response = response.text.toString(),
                text = "",
            )
        }
    }

    fun setImage(image: Bitmap) {
        _imageState.value = _imageState.value.copy(image = image)
    }

    fun setText(text: String) {
        _imageState.value = _imageState.value.copy(text = text)
    }

}

data class ImageUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val response: String = "",
    val image: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val text: String = ""
)