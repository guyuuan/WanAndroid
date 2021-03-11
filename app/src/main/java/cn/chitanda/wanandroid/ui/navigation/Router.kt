package cn.chitanda.wanandroid.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import cn.chitanda.wanandroid.ui.compose.LocalNavController

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:37
 * @Description:
 */
@ExperimentalMaterialApi
@Composable
fun Router(navController: NavHostController = rememberNavController()) {
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
        ) {
            NavHost(navController = navController, startDestination = initialRoute) {
                route()
            }
        }
    }
}