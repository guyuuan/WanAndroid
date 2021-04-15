package cn.chitanda.wanandroid.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.paging.ExperimentalPagingApi
import cn.chitanda.wanandroid.ui.compose.LocalNavController

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:37
 * @Description:
 */
@ExperimentalPagingApi
@ExperimentalMaterialApi
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