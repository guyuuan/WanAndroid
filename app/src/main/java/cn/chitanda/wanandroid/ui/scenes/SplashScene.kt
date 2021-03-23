package cn.chitanda.wanandroid.ui.scenes

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.compose.navigate
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.ui.compose.LocalNavController
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.navigation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:47
 * @Description:
 */
@Composable
fun SplashScene() {
    val viewModel = LocalUserViewModel.current
    val urls by viewModel.imageUrl.collectAsState()
    val context = LocalContext.current
    var imageUrl by mutableStateOf("")
    var downloadStats by remember { mutableStateOf(false) }
    val navController = LocalNavController.current
    val cachedFile = File(LocalContext.current.externalCacheDir, "cachedFile")
    val scope = rememberCoroutineScope()
    if (downloadStats || cachedFile.exists()) {
        NetworkImage(
            url = cachedFile.toUri().toString(),
            Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    LaunchedEffect(key1 = downloadStats) {
        launch(Dispatchers.IO) {
            delay(2500)
            if (downloadStats || cachedFile.exists()) {
                withContext(Dispatchers.Main) {
                    viewModel.checkUserData {
                        navController.popBackStack()
                        if (it) {
                            navController.navigate(Route.Home.id)
                        } else {
                            navController.navigate(Route.Login.id)
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = urls) {
        if (imageUrl.isBlank()&&urls.isNotEmpty()) {
            val mmkv = MMKV.defaultMMKV()
            val url = mmkv?.decodeString(Route.Splash.id, "") ?: ""
            imageUrl = if (url.isNotBlank()) {
                url
            } else {
                viewModel.imageUrl.value.first()
            }
            if (!cachedFile.exists()) {
                Glide.with(context).asBitmap().load(imageUrl)
                    .addListener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            downloadStats = true
                            return true
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            downloadStats = true
                            scope.launch(Dispatchers.IO) {
                                context.contentResolver.openOutputStream(cachedFile.toUri()).use {
                                    resource?.compress(Bitmap.CompressFormat.PNG, 100, it)
                                }
                            }
                            return true
                        }
                    }).submit()
            }
        }
    }
}