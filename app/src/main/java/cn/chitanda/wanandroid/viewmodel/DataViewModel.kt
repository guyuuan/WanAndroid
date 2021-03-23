package cn.chitanda.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import cn.chitanda.wanandroid.data.database.CacheRepository
import cn.chitanda.wanandroid.data.paging.RemoteArticleDataSource
import cn.chitanda.wanandroid.data.paging.RemoteBannerDataSource

/**
 * @Author:       Chen
 * @Date:         2021/3/10 17:40
 * @Description:
 */
class DataViewModel(application: Application) : AndroidViewModel(application) {

    private val cacheRepository = CacheRepository.getInstance(application.applicationContext)

    @ExperimentalPagingApi
    val articles by lazy {
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            remoteMediator = RemoteArticleDataSource(application.applicationContext),
            pagingSourceFactory = {
                cacheRepository.getCachedArticles()
            }).flow.cachedIn(viewModelScope)
    }

    @ExperimentalPagingApi
    val banners by lazy {
        Pager(config = PagingConfig(pageSize = 5, enablePlaceholders = true),
            remoteMediator = RemoteBannerDataSource(application.applicationContext),
            pagingSourceFactory = {
                cacheRepository.getCachedBanners()
            }).flow.cachedIn(viewModelScope)
    }
}