package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:47
 * @Description:
 */
@Composable
fun SplashScene() {
    val userViewModel = LocalUserViewModel.current
    val imageUrl by userViewModel.imageUrl.collectAsState()
    if (imageUrl.isNotBlank()) {
        NetworkImage(
            url = imageUrl,
            contentDescription = "SplashScene", modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth,
            onLoading = {
                Center(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }, onFailure = {
                Center(modifier = Modifier.fillMaxSize()) {
                    Text(text = it.toString())
                }
            }
        )
    }

}