package cn.chitanda.wanandroid.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.bean.Banner
import cn.chitanda.wanandroid.data.bean.BingImage
import cn.chitanda.wanandroid.data.database.dao.ArticleDao
import cn.chitanda.wanandroid.data.database.dao.BannerDao
import cn.chitanda.wanandroid.data.database.dao.BingImageDao

/**
 * @Author:       Chen
 * @Date:         2021/3/12 14:25
 * @Description:
 */
@Database(
    entities = [Article.Data::class, Banner::class, BingImage.Image::class],
    version = 3,
    exportSchema = false
)
abstract class CacheDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun bannerDao(): BannerDao
    abstract fun bingImageDao(): BingImageDao

    companion object {
        @Volatile
        private var instance: CacheDataBase? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: create(context).also { instance = it }
        }

        private fun create(context: Context) =
            Room.databaseBuilder(context, CacheDataBase::class.java, "cache-db")
                .fallbackToDestructiveMigration()
                .build()
    }
}