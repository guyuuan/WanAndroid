package cn.chitanda.wanandroid.data.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import cn.chitanda.wanandroid.data.DataRepository
import cn.chitanda.wanandroid.data.bean.BingImage
import cn.chitanda.wanandroid.data.database.CacheRepository
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

/**
 * @Author:       Chen
 * @Date:         2021/3/12 13:13
 * @Description:
 */

@ExperimentalPagingApi
class RemoteBingImageDataSource(context: Context) : RemoteMediator<Int, BingImage.Image>() {
    private val cacheRepository by lazy {
        CacheRepository.getInstance(context)
    }
    private val mmkv = MMKV.defaultMMKV()
    private val simpleDateFormat by lazy { SimpleDateFormat("MM-dd") }

    override suspend fun initialize(): InitializeAction {
        val cachedTime = mmkv?.decodeString("", "") ?: ""
        val currentTime = simpleDateFormat.format(System.currentTimeMillis())
        return if (currentTime != cachedTime) super.initialize() else InitializeAction.SKIP_INITIAL_REFRESH
    }

    @ExperimentalPagingApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BingImage.Image>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> {
                    withContext(Dispatchers.IO) {
                        mmkv?.encode(
                            "BingImageCacheTime",
                            simpleDateFormat.format(System.currentTimeMillis())
                        )
                        cacheRepository.clearCache()
                        val images = DataRepository.getTodayImage().images
                        cacheRepository.cacheBingImage(images)
                        MediatorResult.Success(endOfPaginationReached = images.isEmpty())
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
