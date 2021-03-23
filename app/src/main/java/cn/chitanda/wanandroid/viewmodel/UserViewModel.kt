package cn.chitanda.wanandroid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import cn.chitanda.wanandroid.data.bean.User
import cn.chitanda.wanandroid.data.database.CacheRepository
import cn.chitanda.wanandroid.data.network.NetworkRepository
import cn.chitanda.wanandroid.data.paging.RemoteBingImageDataSource
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
    private val cacheRepository = CacheRepository.getInstance(application.applicationContext)

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user

    @ExperimentalPagingApi
    val images by lazy {
        Pager(
            config = PagingConfig(pageSize = 8, enablePlaceholders = true),
            remoteMediator = RemoteBingImageDataSource(application.applicationContext),
            pagingSourceFactory = {
                cacheRepository.getCachedBingImages()
            }).flow.cachedIn(viewModelScope)
    }

    fun login(username: String, password: String, callback: (Int, String) -> Unit) {
        if (username.isBlank() || password.isBlank()) return
        launch {
            val response =
                NetworkRepository.instance.login(username, password)
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

    fun checkUserData(callback: (Boolean) -> Unit) {
        val mmkv = MMKV.defaultMMKV() ?: return
        val username = mmkv.getString("username", "") ?: ""
        val password = mmkv.getString("password", "") ?: ""
        login(username, password) { _, _ ->

        }
        callback(mmkv.getStringSet("cookie", emptySet())?.isEmpty() == false)
    }

    fun logout() {
        val mmkv = MMKV.defaultMMKV() ?: return
        mmkv.clearAll()
        launch {
            _user.emit(null)
        }
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