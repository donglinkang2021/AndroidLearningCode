package com.linkdom.hellojetpackcompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linkdom.hellojetpackcompose.ui.theme.HelloJetPackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloJetPackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current

    val counter = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (counter.value == 0) {
            Text(text = "这是我的第一个Compose程序")
        } else {
            Text(text = "这是我的第一个Compose程序, 计数值为${counter.value}")
        }

        // 按下按钮后，下面的ROW会添加一个新的书本
        OutlinedButton(
            onClick = {
                // 打开文件资源管理器选择书本
                counter.value++
            },
            modifier = Modifier
                .shadow(0.dp)
                .padding(0.dp)
        ) {
            Text(text = "添加新书")
        }

        val scrollState = rememberScrollState()
        // 添加新书
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(
                    orientation = Orientation.Horizontal,
                    state = scrollState
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until counter.value) {
                AddNewBook(context = context, bookName = "书名$i")
            }
        }
    }


}

@Composable
fun AddNewBook(context: Context,bookName: String) {
    Column(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AddBookIcon(context = context)
        Text(text = bookName)
    }
}


@Composable
fun AddBookIcon(context: Context) {
    Image(
        painter = painterResource(id = R.drawable.baseline_book_24),
        contentDescription = "icon",
        modifier = Modifier
            .shadow(0.dp)
            .height(100.dp)
            .width(80.dp)
            .clickable {
                Toast
                    .makeText(context, "书本被点击了", Toast.LENGTH_SHORT)
                    .show()
            }
    )
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloJetPackComposeTheme {
        Greeting("Android")
//        MyApp()
    }
}