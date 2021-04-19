package cn.chitanda.compose.networkimage.glide

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cn.chitanda.compose.networkimage.core.ProvideImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager

/**
 * @Author:       Chen
 * @Date:         2021/3/8 14:11
 * @Description:
 */

@Composable
private fun context() = LocalContext.current

@Composable
fun ProvideGlideLoader(
    requestManager: RequestManager = Glide.with(context()),
    content: @Composable () -> Unit
) {
    ProvideImageLoader(imageLoader = GlideImageLoader(requestManager), content = content)
}