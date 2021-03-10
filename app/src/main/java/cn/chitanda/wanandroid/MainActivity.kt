package cn.chitanda.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import cn.chitanda.wanandroid.ui.compose.LocalStatusBaeHeight
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.ui.navigation.Router
import cn.chitanda.wanandroid.ui.theme.WanAndroidTheme
import cn.chitanda.wanandroid.utils.px2dp
import com.tencent.mmkv.MMKV

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMKV.initialize(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var statusBarHeight by mutableStateOf(0.dp)
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
                val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
                val height = statusBar.top
                statusBarHeight = height.px2dp().dp
                insets
            }
            val insetsController = remember {
                WindowInsetsControllerCompat(window, window.decorView)
            }

            WanAndroidTheme {
                CompositionLocalProvider(
                    LocalWindowInsetsController provides insetsController,
                    LocalStatusBaeHeight provides statusBarHeight
                ) {
                    Router()
                }
            }
        }
    }

}

