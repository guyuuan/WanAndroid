package cn.chitanda.wanandroid.ui.scenes.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import cn.chitanda.wanandroid.R
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.utils.isLightImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @Author:       Chen
 * @Date:         2021/3/16 16:22
 * @Description:
 */
@Composable
fun Me() {
    val viewModel = LocalUserViewModel.current
    val scope = rememberCoroutineScope()
    val user by viewModel.user.collectAsState()
    val window = LocalWindowInsetsController.current
    val context = LocalContext.current
    val bitmap = context.getDrawable(R.drawable.asuka)?.toBitmap()
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = bitmap?.asImageBitmap()!!,
            contentDescription = "avatar",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        Center(modifier = Modifier.fillMaxSize()) {
            Text(text = "Username:${user.username}")
        }
    }
    DisposableEffect(key1 = bitmap) {
//        window.isAppearanceLightStatusBars = true
        scope.launch(Dispatchers.IO) {
            val isLight = isLightImage(bitmap ?: return@launch)
            window.isAppearanceLightStatusBars = isLight
        }
        onDispose {
            window.isAppearanceLightStatusBars = false
        }
    }
}


