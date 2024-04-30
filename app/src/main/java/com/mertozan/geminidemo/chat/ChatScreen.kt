package com.mertozan.geminidemo.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mertozan.geminidemo.R
import com.mertozan.geminidemo.ui.theme.DarkWhite
import com.mertozan.geminidemo.ui.theme.TransparentBlue
import com.mertozan.geminidemo.ui.theme.poppinsFamily

@Composable
fun ChatScreen() {

    val viewModel = remember { ChatViewModel() }
    val chatUiState = viewModel.chatState.collectAsState().value

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        ), label = ""
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, bottom = 16.dp)
            .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                if (chatUiState.chatList.isNotEmpty()) {
                    items(chatUiState.chatList.size) { index ->
                        ChatCard(
                            chatType = chatUiState.chatList[index].chatType,
                            text = chatUiState.chatList[index].text
                        )
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.start_asking_questions_to_gemini),
                                style = TextStyle(
                                    fontFamily = poppinsFamily,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Blue.copy(alpha = 0.5f),
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(top = 36.dp)
                                    .fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Image(
                                painter = painterResource(id = R.drawable.google_gemini),
                                contentDescription = stringResource(R.string.gemini_ai),
                                modifier = Modifier.size(100.dp),
                                colorFilter = ColorFilter.tint(TransparentBlue)
                            )
                        }
                    }
                }
            })
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            AnimatedVisibility(chatUiState.isLoading) {
                Image(
                    painter = painterResource(id = R.drawable.google_gemini),
                    contentDescription = stringResource(R.string.loading),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 8.dp)
                        .graphicsLayer {
                            rotationZ = angle
                        },
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 36.dp, top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = chatUiState.question,
                    onValueChange = {
                        viewModel.changeQuestion(it)
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.ask_question),
                            style = TextStyle(
                                fontFamily = poppinsFamily,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = DarkWhite,
                    ),
                    maxLines = 10,
                    textStyle = TextStyle(
                        fontFamily = poppinsFamily,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .animateContentSize(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )
                IconButton(
                    onClick = {
                        viewModel.addChatItem(ChatItem(ChatType.USER, chatUiState.question))
                        viewModel.getAnswer()
                    },
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .size(24.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = stringResource(R.string.ask_question),
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.Blue)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOfChatScreen() {
    ChatScreen()
}