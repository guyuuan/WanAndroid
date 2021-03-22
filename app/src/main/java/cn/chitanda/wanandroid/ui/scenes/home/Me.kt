package cn.chitanda.wanandroid.ui.scenes.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.navigate
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalNavController
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.ui.navigation.Route
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
    val navController = LocalNavController.current
    Column(modifier = Modifier.fillMaxSize()) {
        NetworkImage(
            url = imageUrl.random(),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth(),
            onLoading = {
                Center (modifier = Modifier.fillMaxWidth()){
                    CircularProgressIndicator(color= Color.White)
                }
            },
            onGetBitMap = { bitmap ->
                withContext(Dispatchers.IO) {
                    isLightImage = isLightImage(bitmap.asAndroidBitmap())
                }
            }
        )
        Center(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f)
        ) {
            if (user == null) {
                TextButton(
                    onClick = {
                        navController.navigate(Route.Login.id)
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.colors.onPrimary,
                        disabledBackgroundColor = Color.Transparent,
                        disabledContentColor = MaterialTheme.colors.onSurface
                            .copy(alpha = ContentAlpha.disabled)
                    )
                ) {
                    Text(text = "Click to login", style = MaterialTheme.typography.h5)
                }
            } else {
                Text(text = user?.username ?: "", style = MaterialTheme.typography.h5)
            }
        }
    }
    DisposableEffect(key1 = isLightImage) {
        windowInsetsController.isAppearanceLightStatusBars = isLightImage
        onDispose {
            windowInsetsController.isAppearanceLightStatusBars = false
        }
    }
}


