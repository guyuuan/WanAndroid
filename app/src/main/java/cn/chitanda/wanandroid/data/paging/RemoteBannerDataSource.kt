package cn.chitanda.wanandroid.data.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cn.chitanda.wanandroid.data.DataRepository
import cn.chitanda.wanandroid.data.bean.Banner
import cn.chitanda.wanandroid.data.database.CacheRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/12 13:13
 * @Description:
 */

@ExperimentalPagingApi
class RemoteBannerDataSource(context: Context) : RemoteMediator<Int, Banner>() {
    private val cacheRepository by lazy {
        CacheRepository.getInstance(context)
    }

//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.SKIP_INITIAL_REFRESH
//    }

    @ExperimentalPagingApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Banner>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> {
                    withContext(Dispatchers.IO) {
                        cacheRepository.clearCache()
                        val banners = DataRepository.getBanners().data ?: emptyList()
                        cacheRepository.cachedBanners(banners)
                        MediatorResult.Success(endOfPaginationReached = banners.isEmpty())
                    }
                }
                LoadType.APPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
