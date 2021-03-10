package cn.chitanda.wanandroid.ui.scenes

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.navigate
import cn.chitanda.wanandroid.R
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalNavController
import cn.chitanda.wanandroid.ui.navigation.Route
import cn.chitanda.wanandroid.ui.theme.AvatarBorderColors
import cn.chitanda.wanandroid.utils.px2dp
import cn.chitanda.wanandroid.viewmodel.UserViewModel
import com.tencent.mmkv.MMKV

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:49
 * @Description:
 */
@Composable
fun LoginScene() {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val viewModel = viewModel<UserViewModel>()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var avatarY by remember { mutableStateOf(0.dp) }
    Center(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primarySurface)
    ) {
        Card(elevation = 2.dp, modifier = Modifier.onSizeChanged {
            avatarY = -(it.height / 2).px2dp().dp
        }) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(36.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text(text = "Username")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    label = {
                        Text(text = "Password")
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(onClick = {
                    viewModel.login(username, password) { errorCode, msg ->
                        if (errorCode == 0) {
                            navController.navigate(Route.Home.id)
                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(text = "Login")
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(x = 0.dp, y = avatarY)
                .size(86.dp)
                .shadow(3.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White)
                .border(
                    shape = CircleShape,
                    border = BorderStroke(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = AvatarBorderColors,
                            start = Offset.Zero,
                            end = Offset.Infinite
                        )
                    )
                )
                .border(width = 6.dp, color = Color.White, shape = CircleShape)
        )
    }
    LaunchedEffect(key1 = Route.Home.id) {
        val mmkv = MMKV.defaultMMKV() ?: return@LaunchedEffect
        username = mmkv.decodeString("username", "").toString()
        password = mmkv.decodeString("password", "").toString()
    }
}