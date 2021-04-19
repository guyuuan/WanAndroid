package cn.chitanda.compose.networkimage.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import cn.chitanda.compose.photoview.PhotoView

/**
 * @Author:       Chen
 * @Date:         2021/3/8 14:23
 * @Description:
 */

val LocalImageLoader = staticCompositionLocalOf<ImageLoader> {
    noLocalProvidedFor("LocalContext")
}


@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Fit,
    onLoading: @Composable () -> Unit = {},
    onFailure: @Composable (Throwable) -> Unit = {},
    enableGesture: Boolean = false,
    onGetBitMap: suspend (ImageBitmap) -> Unit = {},
    onSuccess: @Composable (ImageBitmap) -> Unit = {
        if (enableGesture) {
            PhotoView(
                bitmap = it,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        } else {
            Image(
                bitmap = it,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter
            )
        }
        LaunchedEffect(key1 = it) {
            onGetBitMap.invoke(it)
        }
    }
) {
    NetworkImage(url = url, modifier, content = { state ->
        state.run {
            when (this) {
                is RikaState.Loading -> {
                    onLoading()
                }
                is RikaState.Success -> {
                    onSuccess(result)
                }
                is RikaState.Failure -> {
                    onFailure(error)
                }
            }
        }
    })
}

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier = Modifier,
    content: @Composable (state: RikaState<ImageBitmap>) -> Unit
) {
    var state by remember { mutableStateOf<RikaState<ImageBitmap>>(RikaState.Loading) }
    var size by remember(calculation = { mutableStateOf(IntSize.Zero) })
    if (size != IntSize.Zero) {
        LoadImage(imageConfig = ImageConfig(
            id = ImageId.Path(url),
            size = size
        ), onLoaded = {
            state = RikaState.Success(it)
        }, onFailure = { RikaState.Failure(it) })
    }
    Box(
        modifier = modifier then Modifier.onSizeChanged { boxSize ->
            if (size != boxSize) size = boxSize
        }
    ) {
        content(state)
    }
}

@Composable
fun LoadImage(
    imageConfig: ImageConfig<*>,
    onLoaded: (ImageBitmap) -> Unit,
    onFailure: (Throwable) -> Unit
) {
    val imageLoader = LocalImageLoader.current
    DisposableEffect(key1 = imageLoader, effect = {
        val cancelable = imageLoader.getImage(imageConfig, onLoaded, onFailure)
        onDispose {
            cancelable.cancel()
        }
    })
}

@Composable
fun ProvideImageLoader(imageLoader: ImageLoader, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalImageLoader provides imageLoader, content = content)
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}