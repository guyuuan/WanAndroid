package cn.chitanda.wanandroid.ui.scenes.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.R
import cn.chitanda.wanandroid.data.bean.User
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalNavController
import cn.chitanda.wanandroid.ui.compose.LocalUserViewModel
import cn.chitanda.wanandroid.ui.compose.LocalWindowInsetsController
import cn.chitanda.wanandroid.ui.navigation.Route
import cn.chitanda.wanandroid.utils.isLightImage
import cn.chitanda.wanandroid.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/16 16:22
 * @Description:
 */
@ExperimentalPagingApi
@Composable
fun Me() {
    val viewModel = LocalUserViewModel.current
    val user by viewModel.user.collectAsState()
    val images = viewModel.images.collectAsLazyPagingItems()
    var isLightImage by mutableStateOf(false)
    val windowInsetsController = LocalWindowInsetsController.current
    Column(modifier = Modifier.fillMaxSize()) {
        if (images.loadState.refresh is LoadState.NotLoading && images.snapshot().isNotEmpty()) {
            NetworkImage(
                url = images.snapshot().items.random().imageUrl,
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(),
                onLoading = {
                    Center(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(color = Color.White)
                    }
                },
                onGetBitMap = { bitmap ->
                    withContext(Dispatchers.IO) {
                        isLightImage = isLightImage(bitmap.asAndroidBitmap())
                    }
                }
            )
        }
        UserInfo(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8f),
            user = user
        )
    }
    DisposableEffect(key1 = isLightImage) {
        windowInsetsController.isAppearanceLightStatusBars = isLightImage
        onDispose {
            windowInsetsController.isAppearanceLightStatusBars = false
        }
    }
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    user: User?,
    navController: NavController = LocalNavController.current,
    viewModel: UserViewModel = LocalUserViewModel.current
) {
    Column(
        modifier = modifier then Modifier
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Top
    ) {
        if (user != null) {
            with(user) {
                UserInfoItem(title = "Username") {
                    Text(text = username, style = MaterialTheme.typography.h5)
                }
                UserInfoItem(title = "CoinCount") {
                    Text(text = coinCount.toString(), style = MaterialTheme.typography.h5)
                }
                UserInfoItem {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Collections", style = MaterialTheme.typography.h5)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_right),
                            contentDescription = ""
                        )
                    }
                }
                Box(
                    //会被底部导航栏遮挡
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                        .fillMaxSize()
                        .wrapContentHeight(
                            align = Alignment.Bottom
                        ), contentAlignment = Alignment.BottomCenter
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        onClick = {
                            viewModel.logout()
                            navController.navigate(Route.Login.id)
                        }, colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White,
                            disabledContentColor = Color.DarkGray
                        )
                    ) {
                        Text(text = "Logout", style = MaterialTheme.typography.h6)
                    }
                }
            }
        } else {
           Center(Modifier.fillMaxSize()) {
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
            }
        }
    }
}

@Composable
fun UserInfoItem(
    modifier: Modifier = Modifier,
    title: String = "",
    content: @Composable () -> Unit
) {
    Card(modifier = modifier then Modifier.padding(vertical = 12.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (title.isNotBlank()) {
                Text(text = title, style = MaterialTheme.typography.subtitle1)
                Divider(Modifier.padding(vertical = 4.dp))
            }
            content()
        }
    }
}