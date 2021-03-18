package cn.chitanda.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import cn.chitanda.compose.networkimage.glide.ProvideGlideLoader
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.compose.LocalWindow
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.ui.navigation.Router
import cn.chitanda.wanandroid.ui.theme.WanAndroidTheme
import cn.chitanda.wanandroid.viewmodel.UserViewModel
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    @ExperimentalAnimatedInsets
    @ExperimentalPagingApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val insetsController = remember {
                WindowInsetsControllerCompat(window, window.decorView)
            }
            val userViewModel = viewModel<UserViewModel>()
            WanAndroidTheme {
                CompositionLocalProvider(
                    LocalWindowInsetsController provides insetsController,
                    LocalUserViewModel provides userViewModel,
                    LocalWindow provides window
                ) {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        ProvideGlideLoader {
                            Router()
                        }
                    }
                }
            }
        }
    }

}

