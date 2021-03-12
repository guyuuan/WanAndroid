package cn.chitanda.wanandroid.data.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.database.CacheRepository
import cn.chitanda.wanandroid.data.network.NetworkRepository

/**
 * @Author:       Chen
 * @Date:         2021/3/12 13:13
 * @Description:
 */
private const val DEFAULT_START_PAGE = 1
private const val DEFAULT_PAGE_SIZE = 20

@ExperimentalPagingApi
class RemoteArticleDataSource(context: Context) : RemoteMediator<Int, Article.Data>() {
    private val cacheRepository = CacheRepository.getInstance(context)

    @ExperimentalPagingApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article.Data>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                DEFAULT_START_PAGE
            }
            LoadType.APPEND -> {
                (state.anchorPosition ?: DEFAULT_PAGE_SIZE) + 1
            }
            LoadType.PREPEND -> {
                DEFAULT_START_PAGE
            }
        }
        return try {
            val articles = NetworkRepository.instance.getHomeArticles(page).data?.datas
                ?: throw RuntimeException("failed get articles in page $page")
            cacheRepository.cacheArticles(articles)
            MediatorResult.Success(endOfPaginationReached = articles.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
