package cn.chitanda.wanandroid.data.network

import android.util.Log

/**
 * @Author:       Chen
 * @Date:         2021/3/10 13:20
 * @Description:
 */
private const val TAG = "NetworkRepository"

class NetworkRepository private constructor() {
    init {
        Log.d(TAG, "$TAG init")
    }

    companion object {
        private var _instance: NetworkRepository? = null
        val instance: NetworkRepository
            get() = _instance ?: synchronized(this) {
                _instance ?: NetworkRepository().also { _instance = it }
            }
    }

    private val api by lazy { RetrofitCreator.create(Api::class.java) }

    suspend fun login(username: String, password: String) =
        api.login(username = username, password = password)

    suspend fun getHomeArticleList(page: Int) =
        api.getHomeArticleList(page = page)
}