package cn.chitanda.wanandroid.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:44
 * @Description:
 */
@Composable
fun Center(modifier: Modifier, content: @Composable () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        content.invoke()
    }
}