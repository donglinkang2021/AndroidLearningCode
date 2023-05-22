package com.linkdom.drawapp.ui

import com.linkdom.drawapp.base.SingleFlowViewModel
import com.linkdom.drawapp.data.DigitalInkProvider
import com.linkdom.drawapp.data.TranslatorProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigitalInkViewModelImpl @Inject constructor(
    private val digitalInkProvider: DigitalInkProvider, // 用于数字墨水绘制的提供者
    private val translatorProvider: TranslatorProvider // 用于翻译的提供者
): ViewModel(), DigitalInkViewModel {

    private var finishRecordingJob: Job? = null  // 用于在笔画完成时完成记录的任务

    // 检查 ML Kit 模型是否已下载并始终保持最新状态的状态流
    private val _digitalInkModelStatus = digitalInkProvider.checkIfModelIsDownlaoded()
        .flatMapLatest { status ->
            if (status == MLKitModelStatus.Downloaded) // 如果已下载，则返回状态流
                flowOf(status)
            else
                digitalInkProvider.downloadModel() // 如果未下载，则下载模型并返回状态流
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, MLKitModelStatus.NotDownloaded) // 将状态流限定在 ViewModel 的作用域内

    // 检查翻译模型是否已下载并始终保持最新状态的状态流
    private val _translatorModelStatus = translatorProvider.checkIfModelIsDownloaded()
        .stateIn(viewModelScope, SharingStarted.Lazily, MLKitModelStatus.NotDownloaded) // 将状态流限定在 ViewModel 的作用域内

    // 用于接收数字墨水提供者中的预测结果的状态流
    private val _predictions = digitalInkProvider.predictions
        .consumeAsFlow()
        .onEach {
            if (it.isEmpty())
                return@onEach

            setFinalText(text = _finalText.value.plus(it[0])) // 添加预测结果到最终文本中
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()) // 将状态流限制在 ViewModel 的作用域内

    // 用于接收翻译提供者中的翻译结果的状态流
    // region _translation
    private val _translation = translatorProvider.translation
        .consumeAsFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, "") // 将状态流限定在 ViewModel 的作用域内
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
    override val state: StateFlow<DigitalInkViewModel.State>
        get() = combine(
            _digitalInkModelStatus, // 数字墨水模型状态
            _translatorModelStatus, // 翻译模型状态
            _resetCanvas, // 重置画布
            _predictions, // 预测结果
            _translation, // 翻译结果
            _finalText // 最终文本
        ) { result ->
            // 将结果合并为 DigitalInkViewModel 的状态
            val digitalInkModelStatus = result[0] as MLKitModelStatus
            val translatorModelStatus = result[1] as MLKitModelStatus
            val resetCanvas = result[2] as Boolean
            val predictions = result[3] as List<String>
            val translation = result[4] as String
            val finalText = result[5] as String

            // 检查数字墨水和翻译模型是否都已下载
            val areModelsDownloaded =
                (digitalInkModelStatus == MLKitModelStatus.Downloaded) && (translatorModelStatus == MLKitModelStatus.Downloaded)

            // 返回 DigitalInkViewModel 的状态
            DigitalInkViewModel.State(
                resetCanvas = resetCanvas,
                showModelStatusProgress = !areModelsDownloaded,
                finalText = finalText,
                translation = translation,
                predictions = predictions
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            DigitalInkViewModel.State()
        ) // 将状态流限定在 ViewModel 的作用域内，并在 ViewModel 创建时立即计算
    // endregion

    // 处理 DigitalInkViewModel 的事件（在DrawSpace中返回的事情）
    override fun onEvent(event: DigitalInkViewModel.Event) {

        when (event) {
            is DigitalInkViewModel.Event.Pointer -> {
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

            is DigitalInkViewModel.Event.OnStop -> {
                digitalInkProvider.close() // 关闭数字墨水提供者
                translatorProvider.close() // 关闭翻译提供者
            }

            is DigitalInkViewModel.Event.TextChanged -> {
                setFinalText(event.text) // 设置最终文本
            }

            is DigitalInkViewModel.Event.PredictionSelected -> {
                setFinalText(text = _finalText.value.dropLast(1).plus(event.prediction)) // 设置最终文本
            }
        }
    }

    // 设置最终文本的方法
    private fun setFinalText(text: String) {
        _finalText.value = text // 更新最终文本的状态流

        if (text.isNotEmpty())
            translatorProvider.translate(text) // 如果最终文本不为空，则翻译最终文本
    }

    // 用于防抖的常量
    companion object {
        private const val DEBOUNCE_INTERVAL = 1000L
    }
}

// DigitalInkViewModel 接口，继承了 SingleFlowViewModel，用于将 DigitalInkViewModel 与界面绑定
interface DigitalInkViewModel: SingleFlowViewModel<DigitalInkViewModel.Event, DigitalInkViewModel.State> {

    // DigitalInkViewModel 的状态
    data class State(
        val resetCanvas: Boolean = false,
        val showModelStatusProgress: Boolean = false,
        val finalText: String = "",
        val translation: String = "",
        val predictions: List<String> = emptyList(),
    )

    // DigitalInkViewModel 的事件
    sealed class Event {
        data class TextChanged(val text: String): Event()
        data class Pointer(val event: DrawEvent): Event()
        data class PredictionSelected(val prediction: String): Event()

        object OnStop: Event()
    }
}

// 用于提供 DigitalInkViewModel 的本地组合对象
val LocalDigitalInkViewModel = compositionLocalOf<DigitalInkViewModel> {
    error("LocalDigitalViewModelFactory not provided") // 如果没有提供 DigitalInkViewModel，则抛出异常
}

// 用于提供 DigitalInkViewModel 的组合函数
// 这里的 viewModelFactory 参数是一个函数，它返回 DigitalInkViewModel
// 该函数的目的是为了避免在 DigitalInkViewModel 的构造函数中使用 @Composable 注解
// 因为 @Composable 注解只能用于组合函数或组合对象
// 在调用的时候，我们可以使用 viewModelFactory = { DigitalInkViewModel() } 的方式来提供 DigitalInkViewModel
@Composable
fun provideDigitalInkViewModel(viewModelFactory: @Composable () -> DigitalInkViewModel)
        = LocalDigitalInkViewModel provides viewModelFactory.invoke()