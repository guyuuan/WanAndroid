package cn.chitanda.wanandroid.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import cn.chitanda.wanandroid.ui.scenes.HomeScene
import cn.chitanda.wanandroid.ui.scenes.LoginScene
import cn.chitanda.wanandroid.ui.scenes.SplashScene

/**
 * @Author:       Chen
 * @Date:         2021/3/9 11:37
 * @Description:
 */
val initialRoute = Route.Login.id

sealed class Route(val id: String) {
    //    const val Splash = "splash"
//    const val Home = "home"
//    const val Login = "login"
    object Splash : Route("splash")
    object Home : Route("home")
    object Login : Route("login")
}

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

//@Composable
//fun Contents(route: NavBackStackEntry) {
//    Crossfade(targetState = route,animationSpec = tween(1000)) {
//        when (it.arguments?.getString(KEY_ROUTE)) {
//            Route.Splash.id -> SplashScene()
//            Route.Home.id -> HomeScene()
//            Route.Login.id -> LoginScene()
//        }
//    }
//
//}