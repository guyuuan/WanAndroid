package cn.chitanda.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.chitanda.wanandroid.data.DataRepository
import cn.chitanda.wanandroid.data.bean.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @Author:       Chen
 * @Date:         2021/3/10 17:40
 * @Description:
 */
class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val _articles = MutableStateFlow(listOf<Article.Data>())
    private val article = MutableStateFlow(Article())
    val articles: StateFlow<List<Article.Data>> get() = _articles

    init {
        getHomeArticles()
    }

    fun getHomeArticles() {
        launch {
            val response = DataRepository.getHomeArticleList(0)
            if (response.errorCode == 0 && response.data != null) {
                article.emit(response.data)
                _articles.emit(response.data.datas)
            }
        }
    }
}