package cn.chitanda.wanandroid.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.bean.Banner
import cn.chitanda.wanandroid.data.database.dao.ArticleDao
import cn.chitanda.wanandroid.data.database.dao.BannerDao

/**
 * @Author:       Chen
 * @Date:         2021/3/12 14:25
 * @Description:
 */
@Database(entities = [Article.Data::class, Banner::class], version = 2, exportSchema = false)
abstract class CacheDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun bannerDao(): BannerDao

    companion object {
        @Volatile
        private var instance: CacheDataBase? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: create(context).also { instance = it }
        }

        private fun create(context: Context) =
            Room.databaseBuilder(context, CacheDataBase::class.java, "cache-db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("CacheDB", "onCreate: ")
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }
}