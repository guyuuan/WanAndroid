package cn.chitanda.wanandroid.data

import cn.chitanda.wanandroid.data.network.NetworkRepository

/**
 * @Author:       Chen
 * @Date:         2021/3/10 13:32
 * @Description:
 */
object DataRepository {
    private val networkRepository = NetworkRepository.instance

    suspend fun login(username: String, password: String) =
        networkRepository.login(username, password)

    suspend fun getHomeArticles(page: Int) = networkRepository.getHomeArticles(page)

    suspend fun getBanners() = networkRepository.getBanners()
    suspend fun getTodayImage() = networkRepository.getTodayImage()
}