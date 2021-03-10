package cn.chitanda.wanandroid.ui.compose

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:50
 * @Description:
 */

val LocalNavController = staticCompositionLocalOf<NavHostController> { error("No NavController") }
val LocalWindowInsetsController =
    staticCompositionLocalOf<WindowInsetsControllerCompat> { error("No WindowInsetsController") }
val LocalStatusBaeHeight = compositionLocalOf<Dp> { 0.dp }