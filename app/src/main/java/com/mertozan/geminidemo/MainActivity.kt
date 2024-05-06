package com.mertozan.geminidemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mertozan.geminidemo.chat.ChatScreen
import com.mertozan.geminidemo.image.ImageScreen
import com.mertozan.geminidemo.ui.theme.LightBlue
import com.mertozan.geminidemo.ui.theme.poppinsFamily
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val pagerState = rememberPagerState(pageCount = { 2 })
            val scope = rememberCoroutineScope()

            Column {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    divider = {
                        Spacer(modifier = Modifier.height(5.dp))
                    },
                    containerColor = LightBlue,
                    contentColor = Color.White,
                ) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(0)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            modifier = Modifier.padding(top = 8.dp),
                            tint = Color.White
                        )
                        Text(
                            text = getString(R.string.chat),
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                            fontFamily = poppinsFamily,
                        )
                    }
                    Tab(
                        selected = pagerState.currentPage == 1,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier.padding(top = 8.dp),
                            tint = Color.White
                        )
                        Text(
                            text = getString(R.string.image),
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                            fontFamily = poppinsFamily,
                        )
                    }
                }
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> ChatScreen()
                        1 -> ImageScreen()
                    }
                }
            }

        }
    }
}

