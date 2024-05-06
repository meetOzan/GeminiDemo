package com.mertozan.geminidemo.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.mertozan.geminidemo.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {

    private val harassment =
        SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH)
    private val hateSpeech =
        SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
    private val sexualExplicit =
        SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.ONLY_HIGH)
    private val dangerousContent =
        SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.LOW_AND_ABOVE)


    private val config = generationConfig {
        temperature = 0.99f
        topK = 50
        topP = 0.99f
    }

    @[Provides Singleton GeminiPro]
    fun provideGemini(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.GEMINI_API_KEY,
            safetySettings = listOf(
                harassment, hateSpeech, sexualExplicit, dangerousContent
            ),
            generationConfig = config
        )
    }

    @[Provides Singleton GeminiProVision]
    fun provideGeminiVision(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-pro-vision",
            apiKey = BuildConfig.GEMINI_API_KEY,
            safetySettings = listOf(
                harassment, hateSpeech, sexualExplicit, dangerousContent
            ),
            generationConfig = config
        )
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GeminiPro

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GeminiProVision

}