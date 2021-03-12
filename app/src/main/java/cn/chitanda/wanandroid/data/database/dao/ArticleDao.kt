package cn.chitanda.wanandroid.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cn.chitanda.wanandroid.data.bean.Article

/**
 * @Author:       Chen
 * @Date:         2021/3/12 14:10
 * @Description:
 */
@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article.Data>)

    @Query("SELECT * FROM article")
    fun getArticles(): PagingSource<Int, Article.Data>

    @Query("DELETE  FROM article")
    fun deleteAllArticles()
}