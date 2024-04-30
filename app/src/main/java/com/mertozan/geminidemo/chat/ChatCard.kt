package com.mertozan.geminidemo.chat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mertozan.geminidemo.ui.theme.poppinsFamily

@Composable
fun ChatCard(chatType: ChatType, text: String) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(
            when (chatType) {
                ChatType.USER -> 16.dp
                ChatType.AI -> 1.dp
            },
            when (chatType) {
                ChatType.USER -> 1.dp
                ChatType.AI -> 16.dp
            },
            bottomEnd = 16.dp,
            bottomStart = 16.dp

        ),
        colors = CardDefaults.cardColors(
            when (chatType) {
                ChatType.USER -> Color.Blue
                ChatType.AI -> Color.LightGray
            }
        )
    ) {
        Text(
            text = text,
            fontFamily = poppinsFamily,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.9f),
            color = when (chatType) {
                ChatType.USER -> Color.White
                ChatType.AI -> Color.Black
            }
        )

    }
}

@Preview
@Composable
fun PreviewOfCharCard() {
    ChatCard(
        chatType = ChatType.USER,
        text = "Hello, I'm Gemini AI Hello, I'm Gemini AI Hello, I'm Gemini AI Hello, I'm Gemini AI"
    )
}