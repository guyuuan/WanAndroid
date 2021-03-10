package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import cn.chitanda.wanandroid.R

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:47
 * @Description:
 */
@Composable
fun SplashScene() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "SplashScene", modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}