package cn.chitanda.compose.photoview

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale


/**
 * @Author:       Chen
 * @Date:         2021/3/16 10:24
 * @Description:
 */

/*
*
* */
typealias GestureDetector = @Composable (@Composable () -> Unit) -> Unit

@Composable
fun PhotoView(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    gestureDetector: GestureDetector = Multitouch,
) {
    gestureDetector {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier then Modifier.fillMaxSize(),
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}

@Composable
fun PhotoView(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    gestureDetector: GestureDetector = Multitouch,
) {
    gestureDetector {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}

@Composable
fun PhotoView(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    gestureDetector: GestureDetector = Multitouch,
) {
    gestureDetector {
        Image(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier,
            alignment = alignment,
            contentScale = contentScale,
            alpha = alpha,
            colorFilter = colorFilter
        )
    }
}

val Multitouch: GestureDetector = @Composable { content ->
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    when (scale) {
                        in 1f..4f -> {
                            ValueAnimator
                                .ofFloat(scale, scale * 2)
                                .apply {
                                    duration = 300
                                    addUpdateListener { value ->
                                        scale = (value.animatedValue as Float)
                                    }
                                    start()
                                }
                        }
                        else -> {
                            val animatorSet = AnimatorSet()
                            val scaleAnim = ValueAnimator
                                .ofFloat(scale, 1f)
                                .apply {
                                    duration = 300
                                    addUpdateListener { value ->
                                        scale = (value.animatedValue as Float)
                                    }
                                    start()
                                }
                            val rotationAnim = ValueAnimator
                                .ofFloat(rotation / 360, 0f)
                                .apply {
                                    duration = 300
                                    addUpdateListener { value ->
                                        rotation = (value.animatedValue as Float)
                                    }
                                    start()
                                }
                            val offsetXAnim = ValueAnimator
                                .ofFloat(offset.x, 0f)
                                .apply {
                                    duration = 300
                                    addUpdateListener { value ->
                                        offset = Offset(value.animatedValue as Float, offset.y)
                                    }
                                    start()
                                }
                            val offsetYAnim = ValueAnimator
                                .ofFloat(offset.y, 0f)
                                .apply {
                                    duration = 300
                                    addUpdateListener { value ->
                                        offset = Offset(offset.x, value.animatedValue as Float)
                                    }
                                    start()
                                }
                            animatorSet.playTogether(
                                scaleAnim,
                                rotationAnim,
                                offsetXAnim,
                                offsetYAnim
                            )
                            animatorSet.start()
                        }
                    }
                }
            )
        }
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
            rotationZ = rotation,
            translationX = offset.x,
            translationY = offset.y
        )
        .transformable(state = state)
    ) {
        content.invoke()
    }
}


