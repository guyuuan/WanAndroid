package cn.chitanda.compose.networkimage.core

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntSize


/**
 * @Author:       Chen
 * @Date:         2021/3/8 14:24
 * @Description:
 */

interface ImageLoader {
    fun getImage(
        imageConfig: ImageConfig<*>,
        onSuccess: (ImageBitmap) -> Unit,
        onFailure: (Exception) -> Unit
    ): Cancelable
}

data class ImageConfig<T>(
    val id: ImageId<T>,
    val size: IntSize = IntSize.Zero
)

sealed class ImageId<T> {
    abstract val value: T

    data class Path(override val value: String) : ImageId<String>()
    data class Uri(override val value: android.net.Uri) : ImageId<android.net.Uri>()
    data class File(override val value: java.io.File) : ImageId<java.io.File>()
    data class Resource(override val value: Int) : ImageId<Int>()
}

fun interface Cancelable {
    fun cancel()
}