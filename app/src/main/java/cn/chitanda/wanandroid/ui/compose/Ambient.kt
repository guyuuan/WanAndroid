package cn.chitanda.wanandroid.ui.compose

import android.view.Window
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import cn.chitanda.wanandroid.viewmodel.UserViewModel
import java.util.*

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:50
 * @Description:
 */

val LocalNavController = staticCompositionLocalOf<NavHostController> { error("No NavController") }
val LocalWindowInsetsController =
    staticCompositionLocalOf<WindowInsetsControllerCompat> { error("No WindowInsetsController") }
val LocalSystemBar = compositionLocalOf { 0.dp to 0.dp }
val LocalUserViewModel = compositionLocalOf<UserViewModel> { error("No user ViewModel") }
val LocalWindow = compositionLocalOf<Window> { error("No Window") }