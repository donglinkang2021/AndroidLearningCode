package com.linkdom.drawspace.data

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.vision.digitalink.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions
import com.google.mlkit.vision.digitalink.Ink
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DigitalInkProviderImpl @Inject constructor(): DigitalInkProvider {

    // 用于保存笔画的列表
    override val predictions = Channel<List<String>>(4)

    // 用于构建笔画
    private var strokeBuilder: Ink.Stroke.Builder = Ink.Stroke.builder()

    // 指定识别模型为日语
    private val recognitionModel = DigitalInkRecognitionModel
        .builder(DigitalInkRecognitionModelIdentifier.JA)
        .build()

    // 获取远程模型管理器
    private val remoteModelManager = RemoteModelManager.getInstance()

    // 获取手写识别器
    private val recognizer = DigitalInkRecognition.getClient(
        DigitalInkRecognizerOptions
            .builder(this.recognitionModel)
            .build()
    )

    // 检查模型是否可用
    override fun checkModelAvailability(): Boolean {
        var checkDownloaded = false
        this.remoteModelManager
            .isModelDownloaded(this.recognitionModel)
            .addOnSuccessListener { isDownloaded ->
                checkDownloaded = isDownloaded
            }
        return checkDownloaded
    }

    // 检查模型是否已下载
    override fun checkIfModelIsDownloaded(): Flow<MLKitModelStatus> = callbackFlow {
        trySend(MLKitModelStatus.CheckingDownload)

        this@DigitalInkProviderImpl.remoteModelManager
            .isModelDownloaded(this@DigitalInkProviderImpl.recognitionModel)
            .addOnSuccessListener { isDownloaded ->
                if (isDownloaded)
                    trySend(MLKitModelStatus.Downloaded)
                else
                    trySend(MLKitModelStatus.NotDownloaded)
            }
            .addOnCompleteListener { close() }
            .addOnFailureListener {
                it.printStackTrace()
                close(it)
            }

        awaitClose { cancel() }
    }

    // 下载模型
    override fun downloadModel(): Flow<MLKitModelStatus> = callbackFlow {
        val downloadConditions = DownloadConditions.Builder()
            .build()

        trySend(MLKitModelStatus.Downloading)
        this@DigitalInkProviderImpl.remoteModelManager
            .download(this@DigitalInkProviderImpl.recognitionModel, downloadConditions)
            .addOnSuccessListener {
                trySend(MLKitModelStatus.Downloaded)
            }
            .addOnCompleteListener { close() }
            .addOnFailureListener {
                it.printStackTrace()
                close(it)
            }

        awaitClose { cancel() }
    }

    // 记录笔画
    override fun record(x: Float, y: Float) {
        val point = Ink.Point.create(x, y)
        this.strokeBuilder.addPoint(point)
    }

    // 结束记录笔画
    override fun finishRecording() {
        val stroke = this.strokeBuilder.build()

        val inkBuilder = Ink.builder()
        inkBuilder.addStroke(stroke)

        // 将笔画传递给识别器
        // 识别器会返回一个候选列表给predictions
        this.recognizer.recognize(inkBuilder.build())
            .addOnCompleteListener {
                this.strokeBuilder = Ink.Stroke.builder()
            }
            .addOnSuccessListener { result -> this.predictions.trySend(result.candidates.map { it.text }) }
            .addOnFailureListener { it.printStackTrace() }
    }

    override fun close() {
        this.recognizer.close()
    }
}

interface DigitalInkProvider {

    val predictions: Channel<List<String>>

    fun finishRecording()
    fun record(x: Float, y: Float)

    fun downloadModel(): Flow<MLKitModelStatus>
    fun checkIfModelIsDownloaded(): Flow<MLKitModelStatus>
    fun checkModelAvailability(): Boolean

    fun close()
}