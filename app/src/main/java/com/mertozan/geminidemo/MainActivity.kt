package com.mertozan.geminidemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import com.mertozan.geminidemo.chat.ChatScreen
import com.mertozan.geminidemo.image.ImageScreen

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val pagerState = rememberPagerState(pageCount = { 2 })

            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> ChatScreen()
                    1 -> ImageScreen()
                }
            }
        }
    }
}

