package cn.chitanda.wanandroid.ui.compose

import android.view.Window
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import cn.chitanda.wanandroid.ui.scenes.home.Tab
import cn.chitanda.wanandroid.viewmodel.DataViewModel
import cn.chitanda.wanandroid.viewmodel.UserViewModel

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:50
 * @Description:
 */

val LocalNavController = staticCompositionLocalOf<NavHostController> { error("No NavController") }
val LocalWindowInsetsController =
    staticCompositionLocalOf<WindowInsetsControllerCompat> { error("No WindowInsetsController") }
val LocalUserViewModel = compositionLocalOf<UserViewModel> { error("No user view model") }
val LocalDataViewmodel = compositionLocalOf<DataViewModel> { error("No data view model") }
val LocalWindow = compositionLocalOf<Window> { error("No Window") }