package com.linkdom.learningcomposetutorial

import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.learningcomposetutorial.ui.theme.LearningComposeTutorialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningComposeTutorialTheme{
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    Conversation(SampleData.conversationSample)
                    JetpackCompose()
                }
            }
        }
    }
}

@Preview
@Composable
fun JetpackCompose(){
    Card {
        var expanded by remember { mutableStateOf(false) }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.emoji),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clickable { expanded = !expanded }
            )
            AnimatedVisibility(
                visible = expanded,
                enter = expandHorizontally(), // we can also use expandHorizontally() or expandIn()
                exit = shrinkHorizontally() // we can also use shrinkHorizontally() or shrinkOut()
            ) {
                Text(
                    text = "Hello Jetpack Compose",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}





