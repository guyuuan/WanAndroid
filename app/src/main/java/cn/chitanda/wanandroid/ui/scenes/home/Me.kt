package cn.chitanda.wanandroid.ui.scenes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.utils.isLightImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/16 16:22
 * @Description:
 */
@Composable
fun Me() {
    val viewModel = LocalUserViewModel.current
    val user by viewModel.user.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    var isLightImage by mutableStateOf(false)
    val windowInsetsController = LocalWindowInsetsController.current
    Column(modifier = Modifier.fillMaxSize()) {
        NetworkImage(
            url = imageUrl,
            contentDescription = "avatar",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            onGetBitMap = { bitmap ->
                withContext(Dispatchers.IO) {
                    isLightImage = isLightImage(bitmap.asAndroidBitmap())
                }
            }
        )
        Center(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(text = "Username:${user.username}")
        }
    }
    DisposableEffect(key1 = isLightImage) {
        windowInsetsController.isAppearanceLightStatusBars = isLightImage
        onDispose {
            windowInsetsController.isAppearanceLightStatusBars = false
        }
    }
}


