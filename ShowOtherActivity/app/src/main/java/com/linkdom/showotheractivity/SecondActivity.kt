package com.linkdom.showotheractivity


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linkdom.showotheractivity.ui.theme.ShowOtherActivityTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowOtherActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MySecondScreen()
                }
            }
        }
    }
}

@Composable
fun MySecondScreen() {
    val activity = LocalContext.current as Activity
    val info = activity.intent.getStringExtra("info")?:"第二个Activity"
    val userInput = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = info, color = Color.Blue, fontSize = 30.sp)
//        Spacer(modifier = Modifier.height(16.dp))
//        TextField(
//            value = userInput.value,
//            onValueChange = { userInput.value = it },
//            placeholder = { Text(text = stringResource(id = R.string.input_info)) }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            activity.setResultAndReturn(userInput)
//        }) {
//            Text(text = stringResource(id = R.string.return_info))
//        }
    }
}

private fun Activity.setResultAndReturn(
    userInput: MutableState<String>
) {
    val intent = Intent()
    val resultString = userInput.value.ifEmpty { "No data" }
    intent.putExtra("info", resultString)
    setResult(Activity.RESULT_OK, intent)
    finish()
}