package cn.chitanda.wanandroid.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cn.chitanda.wanandroid.ui.compose.LocalNavController

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:37
 * @Description:
 */
@Composable
fun Router(navController: NavHostController = rememberNavController()) {
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(navController = navController, startDestination = initialRoute) {
            route()
        }
    }
}