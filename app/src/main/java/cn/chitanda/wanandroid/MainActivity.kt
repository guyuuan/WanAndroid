package cn.chitanda.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import cn.chitanda.compose.networkimage.glide.ProvideGlideLoader
import cn.chitanda.wanandroid.ui.compose.LocalSystemBar
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.compose.LocalWindow
import cn.chitanda.wanandroid.ui.navigation.Router
import cn.chitanda.wanandroid.ui.theme.WanAndroidTheme
import cn.chitanda.wanandroid.utils.px2dp
import cn.chitanda.wanandroid.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    @ExperimentalPagingApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            var statusBarHeight by mutableStateOf(0.dp)
            var navBarHeight by mutableStateOf(0.dp)
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
                val systemBar = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                val top = systemBar.top
                val bottom = systemBar.bottom
                statusBarHeight = top.px2dp().dp
                navBarHeight = bottom.px2dp().dp
                insets
            }
            val insetsController = remember {
                WindowInsetsControllerCompat(window, window.decorView)
            }
            val userViewModel = viewModel<UserViewModel>()
            WanAndroidTheme {
                CompositionLocalProvider(
                    LocalWindowInsetsController provides insetsController,
                    LocalSystemBar provides (statusBarHeight to navBarHeight),
                    LocalUserViewModel provides userViewModel,
                    LocalWindow provides window
                ) {
                    ProvideGlideLoader {
                        Router()
                    }
                }
            }
        }
    }

}

