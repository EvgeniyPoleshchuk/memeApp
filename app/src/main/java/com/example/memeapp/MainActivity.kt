@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memeapp

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.memeapp.ui.theme.LightGrey
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MemeMain()
        }
    }
}


@Composable
private fun MemeMain() {
    var randomDigit = remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(true) }
    var memeList = ListMemes()
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        Modifier
            .fillMaxSize()
            .background(LightGrey)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextField(text,
                    onValueChange = { text = it },
                    label = { Text("Введите свой вопрос", fontWeight = FontWeight.Bold) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LightGrey,
                        unfocusedContainerColor = LightGrey,
                        disabledContainerColor = LightGrey,
                    )
                )
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        randomDigit.value = Random.nextInt(memeList.size)
                        expanded = true
                        expanded2 = false
                        keyboardController?.hide()
                    }
                }, enabled = expanded2) {
                    Image(painterResource(R.drawable.baseline_search_24), "", Modifier.size(50.dp))
                }
            }
        }
        Box(
            Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            if (expanded) {
                AsyncImage(
                    memeList[randomDigit.value].url,
                    "", modifier = Modifier.size(300.dp, 300.dp)
                )
            }
        }

        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Image(painterResource(R.drawable._f44d), "",
                    Modifier
                        .background(Color.LightGray)
                        .size(100.dp, 70.dp)
                        .clickable {
                            expanded = false
                            expanded2 = true
                        })
                Image(painterResource(R.drawable._f44e_bolshoi_palets_vniz), "",
                    Modifier
                        .background(Color.LightGray)
                        .size(100.dp, 70.dp)
                        .clickable {
                            expanded = false
                            expanded2 = true
                        })
            }
        }
    }
}


@Composable
fun ListMemes(): List<Meme> {
    val defMeme = listOf(Meme(0, 0, "", "", "", 0))
    var data by remember { mutableStateOf(defMeme) }
    LaunchedEffect(key1 = true) {
        try {
            val response = RetroFit.memeDataApi.getData()
            data = response.data.memes
        } catch (e: Exception) {
            // Обработка ошибки
        }
    }
    return data
}


@Preview(showBackground = true)
@Composable
fun MemeMainPreview() {
    MemeMain()
}