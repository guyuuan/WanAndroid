package cn.chitanda.wanandroid.data.database

import android.content.Context
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.bean.Banner
import cn.chitanda.wanandroid.data.bean.BingImage

/**
 * @Author:       Chen
 * @Date:         2021/3/12 14:39
 * @Description:
 */
class CacheRepository private constructor(context: Context) {
    private val db by lazy { CacheDataBase.getInstance(context) }

    private val articleDao by lazy { db.articleDao() }
    private val bannerDao by lazy { db.bannerDao() }
    private val bingImageDao by lazy { db.bingImageDao() }

    companion object {
        @Volatile
        private var instance: CacheRepository? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: CacheRepository(context).also { instance = it }
        }
    }

    fun getCachedArticles() = articleDao.getArticles()

    fun cacheArticles(articles: List<Article.Data>) = articleDao.insertArticles(articles)

    fun clearArticles() = articleDao.deleteAllArticles()

    fun getCachedBanners() = bannerDao.getBanners()

    fun cacheBanners(banners: List<Banner>) = bannerDao.insertBanners(banners)

    fun clearBanners() = bannerDao.deleteAllBanners()

    fun getCachedBingImages() = bingImageDao.getBingImages()

    fun cacheBingImage(images: List<BingImage.Image>) = bingImageDao.insertBingImages(images)

    fun clearBingImages() = bingImageDao.deleteAllBingImages()

    fun clearCache() {
        articleDao.deleteAllArticles()
        bannerDao.deleteAllBanners()
        bingImageDao.deleteAllBingImages()
    }
}