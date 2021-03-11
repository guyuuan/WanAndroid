package cn.chitanda.wanandroid.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * @Author:       Chen
 * @Date:         2021/3/11 15:14
 * @Description:
 */
@ExperimentalMaterialApi
@Composable
fun SwipeRefreshBox(content: @Composable () -> Unit) {
    val squareSize = 72.dp
    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states
    val process = swipeableState.offset.value / sizePx
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { from, to -> FractionalThreshold(0.1f) },
                orientation = Orientation.Vertical
            )
    ) {
        content.invoke()
        Box(
            modifier = Modifier
                .graphicsLayer {
                    alpha = process
                }
                .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                .size(60.dp)
                .background(Color.White)
                .shadow(4.dp, shape = CircleShape)
                .clip(CircleShape),
        ) {
            CircularProgressIndicator(
                Modifier
                    .fillMaxSize()
                    .padding(3.dp)
            )
        }
    }
}


@ExperimentalMaterialApi
@Preview
@Composable
fun SwipeRefreshPreview() {
//    SwipeRefreshBox()
}