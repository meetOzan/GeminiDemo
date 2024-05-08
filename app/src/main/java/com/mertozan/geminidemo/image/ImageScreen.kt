package com.mertozan.geminidemo.image

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mertozan.geminidemo.R
import com.mertozan.geminidemo.ui.theme.DarkWhite
import com.mertozan.geminidemo.ui.theme.LightGray
import com.mertozan.geminidemo.ui.theme.TransparentBlue
import com.mertozan.geminidemo.ui.theme.poppinsFamily

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ImageScreen() {

    val viewModel = hiltViewModel<ImageViewModel>()

    val imageUiState = viewModel.imageState.collectAsState().value
    val context = LocalContext.current

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(context.contentResolver, uri)
            )
            viewModel.setImage(bitmap)
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        ), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.ask_with_an_image),
                style = TextStyle(
                    fontFamily = poppinsFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue.copy(alpha = 0.5f),
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp)
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp, start = 12.dp, end = 12.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                TextField(
                    value = imageUiState.text,
                    onValueChange = {
                        viewModel.setText(it)
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
                        focusedContainerColor = DarkWhite,
                        unfocusedContainerColor = LightGray,
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
                    onClick = { pickImage.launch("image/*") },
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .background(TransparentBlue)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = stringResource(R.string.pick_image),
                        tint = DarkWhite
                    )
                }
            }
            AnimatedVisibility(imageUiState.image.width > 1) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        bitmap = imageUiState.image.asImageBitmap(),
                        contentDescription = stringResource(R.string.image),
                        modifier = Modifier
                            .fillMaxSize(0.45f),
                    )
                }
            }
            Spacer(modifier = Modifier.padding(horizontal = 12.dp))
            AnimatedVisibility(visible = imageUiState.response.isNotEmpty()) {
                Text(
                    text = imageUiState.response,
                    style = TextStyle(
                        fontFamily = poppinsFamily,
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
        AnimatedVisibility(imageUiState.isLoading) {
            Image(
                painter = painterResource(id = R.drawable.google_gemini),
                contentDescription = stringResource(R.string.loading),
                modifier = Modifier
                    .size(36.dp)
                    .padding(bottom = 16.dp)
                    .graphicsLayer {
                        rotationZ = angle
                    },
            )
        }
        Button(
            onClick = { viewModel.askWithImage() },
            modifier = Modifier.padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text(
                text = stringResource(R.string.send_prompt),
                style = TextStyle(
                    color = DarkWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                fontFamily = poppinsFamily,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun ImageScreenPreview() {
    ImageScreen()
}