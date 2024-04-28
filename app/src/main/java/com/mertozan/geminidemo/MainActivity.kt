package com.mertozan.geminidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mertozan.geminidemo.ui.theme.GeminiDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeminiDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    val viewModel = remember { GeminiViewModel() }
    val geminiState = viewModel.geminiState.collectAsState().value

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Ask Question", fontSize = 24.sp)
        TextField(value = geminiState.question, onValueChange = {
            viewModel.changeQuestion(it)
        })
        Button(onClick = {
            viewModel.getAnswer()

        }) {
            Text(text = "Ask to Gemini")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Answer", fontSize = 24.sp)
        Text(text = geminiState.answer, fontSize = 16.sp)
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeminiDemoTheme {
        Greeting()
    }
}