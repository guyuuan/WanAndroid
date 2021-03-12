package cn.chitanda.wanandroid.viewmodel

import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import cn.chitanda.wanandroid.data.DataRepository
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.database.CacheRepository
import cn.chitanda.wanandroid.data.paging.RemoteArticleDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Author:       Chen
 * @Date:         2021/3/10 17:40
 * @Description:
 */
class ArticleViewModel(application: Application) : AndroidViewModel(application) {
//        private val _articles = MutableStateFlow(listOf<Article.Data>())
//    private val article = MutableStateFlow(Article())
//    val articles: StateFlow<List<Article.Data>> get() = _articles
//
//    init {
//        getHomeArticles()
//    }
//
//    fun getHomeArticles() {
//        launch {
//            val response = DataRepository.getHomeArticleList(0)
//            if (response.errorCode == 0 && response.data != null) {
//                article.emit(response.data)
//                _articles.emit(response.data.datas)
//                cacheRepository.cacheArticles(response.data.datas)
//            }
//        }
//    }
//
//    fun nextPage() {
//        launch {
//            val response = DataRepository.getHomeArticleList(article.value.curPage)
//            if (response.errorCode == 0 && response.data != null) {
//                article.emit(response.data)
//                _articles.emit(listOf(_articles.value, response.data.datas).flatten())
//            }
//        }
//    }
    @ExperimentalPagingApi
    val articleRemoteMediator = RemoteArticleDataSource(application.applicationContext)

    private val cacheRepository = CacheRepository.getInstance(application.applicationContext)
    @ExperimentalPagingApi
    val articles by lazy {
        Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            remoteMediator = articleRemoteMediator,
            pagingSourceFactory = {
                cacheRepository.getCachedArticles()
            }).flow.cachedIn(viewModelScope)
    }
}