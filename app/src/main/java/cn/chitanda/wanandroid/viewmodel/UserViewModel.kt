package cn.chitanda.wanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.wanandroid.data.DataRepository
import cn.chitanda.wanandroid.data.bean.User
import cn.chitanda.wanandroid.ui.navigation.Route
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/10 13:48
 * @Description:
 */
private const val TAG = "UserViewModel"

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    init {
        getTodayImage()
    }

    private val _imageUrl = MutableStateFlow(emptyList<String>())
    val imageUrl: StateFlow<List<String>> get() = _imageUrl

    fun login(username: String, password: String, callback: (Int, String) -> Unit) {
        if (username.isBlank() || password.isBlank()) return
        launch {
            val response =
                DataRepository.login(username, password)
            if (response.errorCode == 0 && response.data != null) {
                _user.emit(response.data)
                MMKV.defaultMMKV()?.apply {
                    encode("username", username)
                    encode("password", password)
                }
            }
            withContext(Dispatchers.Main) {
                callback.invoke(
                    response.errorCode,
                    response.errorMsg
                )
            }
            Log.d(TAG, "login: ${response.errorCode}")
        }
    }

    private fun getTodayImage() {
        launch {
            val images = DataRepository.getTodayImage().images
            if (images.isNotEmpty()) {
                val url = "https://s.cn.bing.net" + images.first().url
                _imageUrl.emit(images.map { "https://s.cn.bing.net" + it.url })
                val mmkv = MMKV.defaultMMKV() ?: return@launch
                mmkv.encode(Route.Splash.id, url)
            }
        }
    }

    fun checkUserData(callback: (Boolean) -> Unit) {
        val mmkv = MMKV.defaultMMKV() ?: return
        val username = mmkv.getString("username", "") ?: ""
        val password = mmkv.getString("password", "") ?: ""
        login(username, password) { _, _ ->

        }
        callback(mmkv.getStringSet("cookie", emptySet())?.isEmpty() == false)
    }
}

fun ViewModel.launch(
    onError: (Exception) -> Unit = {
        Log.e("ViewModel", "launch: ", it)
    }, block: suspend () -> Unit
) {
    viewModelScope.launch(Dispatchers.IO) {
        try {
            block.invoke()
        } catch (e: Exception) {
            onError.invoke(e)
        }
    }
}