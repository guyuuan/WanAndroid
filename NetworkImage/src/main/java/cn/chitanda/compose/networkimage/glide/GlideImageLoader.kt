package cn.chitanda.compose.networkimage.glide

import android.os.Handler
import android.os.Looper
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.IntSize
import cn.chitanda.compose.networkimage.core.Cancelable
import cn.chitanda.compose.networkimage.core.ImageConfig
import cn.chitanda.compose.networkimage.core.ImageLoader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener


/**
 * @Author:       Chen
 * @Date:         2021/3/8 14:15
 * @Description:
 */
private typealias GlideTarget<T> = com.bumptech.glide.request.target.Target<T>

class GlideImageLoader(private val requestManager: RequestManager) : ImageLoader {
    override fun getImage(
        imageConfig: ImageConfig<*>,
        onSuccess: (ImageBitmap) -> Unit,
        onFailure: (Exception) -> Unit
    ): Cancelable {
        val size = if (imageConfig.size == IntSize.Zero) {
            IntSize(GlideTarget.SIZE_ORIGINAL, GlideTarget.SIZE_ORIGINAL)
        } else {
            imageConfig.size
        }

        val future = requestManager
            .asBitmap()
            .load(imageConfig.id.value)
            .listener(onSuccess = {
                onSuccess(it.asImageBitmap())
            }, onFailure = onFailure)
            .submit(size.width, size.height)
        return Cancelable {
            future.cancel(true)
        }
    }
}

private inline fun <T> RequestBuilder<T>.listener(
    crossinline onSuccess: (T) -> Unit, crossinline onFailure: (Exception) -> Unit = {}
): RequestBuilder<T> {
    return listener(object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: GlideTarget<T>,
            isFirstResource: Boolean
        ): Boolean {
            runOnUIThread { onFailure(e ?: Exception("Unknown Exception")) }
            return true
        }

        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: GlideTarget<T>,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            runOnUIThread { onSuccess(resource) }
            return true
        }
    })
}

private fun runOnUIThread(action: () -> Unit) {
    Handler(Looper.getMainLooper()).post {
        action()
    }
}