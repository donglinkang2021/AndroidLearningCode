package com.linkdom.drawapp.ui

import com.linkdom.drawapp.base.use
import com.linkdom.drawapp.ui.theme.DigitalInkColors // 导入 DigitalInkColors 主题
import com.linkdom.drawapp.ui.theme.Purple700 // 导入 Purple700 主题
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

// DigitalInkScreen 组件，接收一个 modifier 参数
@Preview(showBackground = true)
@Composable
fun DigitalInkScreen(
    modifier: Modifier = Modifier
) {

    val lifecycleOwner = LocalLifecycleOwner.current // 获取当前生命周期拥有者

    val (state, event) = use(LocalDigitalInkViewModel.current, DigitalInkViewModel.State()) // 使用 use 函数获取 viewModel 的状态和事件处理方法

    DisposableEffect(Unit) { // DisposableEffect 可以在组件创建时添加副作用，当组件被销毁时移除副作用
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) // 当生命周期状态变为 ON_STOP 时发送 OnStop 事件
                event(DigitalInkViewModel.Event.OnStop)
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver) // 添加生命周期观察者
        onDispose { lifecycleOwner.lifecycle.removeObserver(lifecycleObserver) } // 组件被销毁时移除观察者
    }

    Box(modifier = modifier) { // 使用 Box 组件包含所有的子组件
        if (state.showModelStatusProgress) {
            // 如果状态中 showModelStatusProgress 为 true，显示正在检查模型的进度条

            ModelStatusProgress(
                statusText = "Checking models...", // 进度条下方显示的文本
                modifier = Modifier
                    .align(Alignment.Center) // 居中对齐
            )

        } else {
            // 显示组件的主体内容

            Column(modifier = Modifier.fillMaxSize()) { // 使用 Column 列表布局嵌套多个子组件

                Card(elevation = 4.dp) { // 使用 Card 组件创建带阴影的卡片
                    Column(modifier = Modifier.fillMaxWidth()) {

                        // region 输入文本框
                        TextField(
                            // 使用 TextField 组件创建文本输入框
                            value = state.finalText, // 绑定 viewModel 的 finalText 字段
                            onValueChange = { event(DigitalInkViewModel.Event.TextChanged(it)) }, // 当文本内容变化时发送 TextChanged 事件
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1, // 最大行数为 1
                            textStyle = TextStyle(color = DigitalInkColors.Text, fontSize = 24.sp), // 指定文本样式
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White, // 背景色为白色
                                placeholderColor = DigitalInkColors.PredictionPlaceholder,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = DigitalInkColors.PredictionText // 光标颜色为主题中的 PredictionText 颜色
                            ),
                            placeholder = {
                                Text(
                                    text = "Enter text(Japanese)", // 当文本框为空时显示的提示文本
                                    fontSize = 24.sp,
                                )
                            },
                            shape = RoundedCornerShape(0.dp) // 指定圆角矩形的圆角半径
                        )
                        // endregion

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)) // 创建一条灰色的水平分割线

                        // region 翻译文本框
                        Text(
                            text = state.translation, // 显示 viewModel 的翻译结果
                            fontSize = 24.sp,
                            color = Purple700, // 文本颜色为主题中的 Purple700 颜色
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                        // endregion

                    }
                }

                Spacer(modifier = Modifier.weight(1f, fill = true)) // 创建一个空间，使用 weight 占用剩余的空间

                // region 预测列表行
                LazyRow( // 使用 LazyRow 组件创建水平滚动列表
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(DigitalInkColors.PredictionBackground) // 列表背景色为主题中的 PredictionBackground 颜色
                ) {
                    items(state.predictions) { prediction -> // 遍历 viewModel 的 predictions 列表，创建多个 Prediction 组件
                        Prediction(
                            text = prediction,
                            onClick = { DigitalInkViewModel.Event.PredictionSelected(it) }
                        )
                    }
                }
                // endregion

                // region 手写区域
                DrawSpace(
                    reset = state.resetCanvas, // 重置画布的标志位
                    onDrawEvent = { event(DigitalInkViewModel.Event.Pointer(it)) }, // 当有手写移动事件时发送 Pointer 事件
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true) // 占用剩余的空间
                )
                // endregion
            }
        }
    }
}

@Composable
fun ModelStatusProgress( // 显示检查模型进度的进度条和文本的组件
    statusText: String,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {

        CircularProgressIndicator( // 创建一个圆形进度条
            modifier = Modifier
                .align(Alignment.CenterHorizontally) // 水平居中对齐
                .padding(vertical = 24.dp) // 上下间距为 24dp
        )

        Text(
            text = statusText, // 显示的文本
            textAlign = TextAlign.Center, // 居中对齐
            fontSize = 18.sp
        )
    }
}

// 预测结果的组件，也就是那一个个打字上面识别的文字
// 到时候使用表情预测应该也可以用它
@Composable
fun Prediction(
    text: String,
    onClick: (String) -> Unit, // 点击事件处理方法
    modifier: Modifier = Modifier
) {

    Row(modifier = modifier) { // 使用 Row 横向排列子组件
        Text(
            text = text, // 显示的文本
            color = DigitalInkColors.PredictionText, // 文本颜色为主题中的 PredictionText 颜色
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp) // 左右间距为 16dp，上下间距为 8dp
                .clickable { onClick.invoke(text) }, // 添加点击事件，点击时调用 onClick 方法并传入文本内容
        )

        Box(modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(DigitalInkColors.PredictionDivider) // 添加一条垂直分割线，颜色为主题中的 PredictionDivider 颜色
        )
    }
}