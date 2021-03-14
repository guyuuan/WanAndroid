package cn.chitanda.wanandroid.data.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.database.CacheRepository
import cn.chitanda.wanandroid.data.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/12 13:13
 * @Description:
 */
private const val DEFAULT_START_PAGE = 0
private const val DEFAULT_PAGE_SIZE = 20

@ExperimentalPagingApi
class RemoteArticleDataSource(context: Context) : RemoteMediator<Int, Article.Data>() {
    private val cacheRepository = CacheRepository.getInstance(context)
    private var nextKey = 0

    @ExperimentalPagingApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article.Data>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    DEFAULT_START_PAGE
                }
                LoadType.APPEND -> {
                    nextKey
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            withContext(Dispatchers.IO) {
                val response =
                    NetworkRepository.instance.getHomeArticles(page)
                val articles = response.data?.datas
                    ?: throw RuntimeException("failed get articles in page $page")
                nextKey = response.data.curPage
                cacheRepository.cacheArticles(articles)
                MediatorResult.Success(endOfPaginationReached = state.pages.size >=response.data.pageCount)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
