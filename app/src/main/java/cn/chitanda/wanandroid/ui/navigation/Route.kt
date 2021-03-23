package cn.chitanda.wanandroid.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.ExperimentalPagingApi
import cn.chitanda.wanandroid.ui.scenes.HomeScene
import cn.chitanda.wanandroid.ui.scenes.LoginScene
import cn.chitanda.wanandroid.ui.scenes.SplashScene

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:37
 * @Description:
 */
val initialRoute = Route.Splash.id

sealed class Route(val id: String) {
    object Splash : Route("splash")
    object Home : Route("home")
    object Login : Route("login")
}

@ExperimentalPagingApi
@ExperimentalMaterialApi
fun NavGraphBuilder.route() {
    composable(Route.Splash.id) {
        SplashScene()
    }
    composable(Route.Login.id) {
        LoginScene()
    }
    composable(Route.Home.id) {
        HomeScene()
    }
}