package com.linkdom.drawspace.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkdom.drawspace.base.SingleFlowViewModel
import com.linkdom.drawspace.data.DigitalInkProvider
import com.linkdom.drawspace.data.MLKitModelStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawViewModelImpl @Inject constructor(
    private val digitalInkProvider: DigitalInkProvider, // 用于数字墨水绘制的提供者
): ViewModel(),DrawViewModel{

    private var finishRecordingJob: Job? = null  // 用于在笔画完成时完成记录的任务

    // 检查 ML Kit 模型是否已下载并始终保持最新状态的状态流
    // region _digitalInkModelStatus
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _digitalInkModelStatus = digitalInkProvider.checkIfModelIsDownloaded()
        .flatMapLatest { status ->
            if (status == MLKitModelStatus.Downloaded) // 如果已下载，则返回状态流
                flowOf(status)
            else
                digitalInkProvider.downloadModel() // 如果未下载，则下载模型并返回状态流
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, MLKitModelStatus.NotDownloaded) // 将状态流限定在 ViewModel 的作用域内
    // endregion

    // 用于接收数字墨水提供者中的预测结果的状态流
    // region _predictions
    private val _predictions = digitalInkProvider.predictions
        .consumeAsFlow()
        .onEach {
            if (it.isEmpty())
                return@onEach

            setFinalText(text = _finalText.value.plus(it[0])) // 添加预测结果到最终文本中
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()) // 将状态流限制在 ViewModel 的作用域内
    // endregion

    // 用于存储最终文本的可变状态流
    // region _finalText
    private val _finalText = MutableStateFlow<String>("")
    // endregion

    // 用于重置画布的可变状态流
    // region _resetCanvas
    private val _resetCanvas = MutableStateFlow<Boolean>(false)
    // endregion

    // 返回 DigitalInkViewModel 状态的 StateFlow
    // region state
    override val state: StateFlow<DrawViewModel.State>
        get() = combine(
            _digitalInkModelStatus, // 数字墨水模型状态
            _resetCanvas, // 重置画布
            _predictions, // 预测结果
            _finalText // 最终文本
        ) { result ->
            // 将结果合并为 DrawViewModel 的状态
            val digitalInkModelStatus = result[0] as MLKitModelStatus
            val resetCanvas = result[2] as Boolean
            val predictions = result[3] as List<String>
            val finalText = result[5] as String
            // 检查数字墨水和翻译模型是否都已下载
            val areModelsDownloaded = (digitalInkModelStatus == MLKitModelStatus.Downloaded)
            // 返回 DrawViewModel 的状态
            DrawViewModel.State(
                resetCanvas = resetCanvas,
                showModelStatusProgress = !areModelsDownloaded,
                finalText = finalText,
                predictions = predictions
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            DrawViewModel.State()
        ) // 将状态流限定在 ViewModel 的作用域内，并在 ViewModel 创建时立即计算
    // endregion

    // 处理 DigitalInkViewModel 的事件（在DrawSpace中返回的事情）
    // region event
    override fun onEvent(event: DrawViewModel.Event) {

        when (event) {
            is DrawViewModel.Event.Pointer -> {
                // region 处理手指按下、移动和抬起的事件
                when (val drawEvent = event.event) {
                    is DrawEvent.Down -> {
                        this.finishRecordingJob?.cancel() // 取消完成记录的任务
                        _resetCanvas.value = false // 重置画布

                        digitalInkProvider.record(drawEvent.x, drawEvent.y) // 记录笔画
                    }

                    is DrawEvent.Move -> {
                        digitalInkProvider.record(drawEvent.x, drawEvent.y) // 记录笔画
                    }

                    is DrawEvent.Up -> {
                        this.finishRecordingJob = viewModelScope.launch {
                            delay(DEBOUNCE_INTERVAL) // 等待一段时间以便完成记录
                            _resetCanvas.value = true // 重置画布
                            digitalInkProvider.finishRecording() // 完成记录
                        }
                    }
                }
                // endregion
            }

            is DrawViewModel.Event.OnStop -> {
                digitalInkProvider.close() // 关闭数字墨水提供者
            }

            is DrawViewModel.Event.TextChanged -> {
                setFinalText(event.text) // 设置最终文本
            }

            is DrawViewModel.Event.PredictionSelected -> {
                setFinalText(text = _finalText.value.dropLast(1).plus(event.prediction)) // 设置最终文本
            }
        }
    }
    // endregion

    // region 辅助函数
    // 设置最终文本的方法
    private fun setFinalText(text: String) {
        _finalText.value = text // 更新最终文本的状态流
    }

    // 用于防抖的常量
    companion object {
        private const val DEBOUNCE_INTERVAL = 1000L
    }
    // endregion

}

interface DrawViewModel: SingleFlowViewModel<DrawViewModel.Event, DrawViewModel.State> {

    // DrawViewModel 的状态：数据类
    data class State(
        val resetCanvas: Boolean = false,
        val showModelStatusProgress: Boolean = false,
        val finalText: String = "",
        val predictions: List<String> = emptyList(),
    )

    // DrawViewModel 的事件：密封类
    sealed class Event {
        data class Pointer(val event: DrawEvent): Event()
        object OnStop: Event()
        data class TextChanged(val text: String): Event()
        data class PredictionSelected(val prediction: String): Event()
    }

}

var LocalDrawViewModel = compositionLocalOf<DrawViewModel> {
    error("LocalDigitalViewModelFactory not provided") // 如果没有提供 DigitalInkViewModel，则抛出异常
}