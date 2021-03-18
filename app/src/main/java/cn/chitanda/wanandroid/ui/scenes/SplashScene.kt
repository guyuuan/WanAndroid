package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.navigate
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.ui.compose.LocalNavController
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:47
 * @Description:
 */
@Composable
fun SplashScene() {
    val userViewModel = LocalUserViewModel.current
    val imageUrl by userViewModel.imageUrl.collectAsState()
    val navController = LocalNavController.current
    if (imageUrl.isNotBlank()) {
        NetworkImage(
            url = imageUrl,
            contentDescription = "SplashScene", modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,
        )
    }
    LaunchedEffect(key1 = imageUrl) {
        withContext(Dispatchers.IO){
            delay(2000)
            withContext(Dispatchers.Main){
                userViewModel.checkUserData { hasUser ->
                    navController.popBackStack()
                    if (hasUser) {
                        navController.navigate(Route.Home.id)
                    } else {
                        navController.navigate(Route.Login.id)
                    }
                }
            }
        }
    }
}