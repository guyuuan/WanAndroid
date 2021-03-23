package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.navigate
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalNavController
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.navigation.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:47
 * @Description:
 */
@Composable
fun SplashScene() {
    val navController = LocalNavController.current
    val viewModel = LocalUserViewModel.current
    val urls by viewModel.imageUrl.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        if (urls.isNotEmpty()) NetworkImage(
            url = urls.random(),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onLoading = {
                Center(Modifier.fillMaxSize()) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        )
    }
    LaunchedEffect(key1 = urls) {
        if (urls.isEmpty()) return@LaunchedEffect
        launch(Dispatchers.IO) {
            delay(3000)
            withContext(Dispatchers.Main) {
                viewModel.checkUserData { isLogin ->
                    navController.popBackStack()
                    if (isLogin) {
                        navController.navigate(Route.Home.id)
                    } else {
                        navController.navigate(Route.Login.id)
                    }
                }
            }
        }
    }
}