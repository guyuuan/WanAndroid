package cn.chitanda.wanandroid.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cn.chitanda.wanandroid.data.bean.Banner
import cn.chitanda.wanandroid.data.bean.BingImage

/**
 * @Author:       Chen
 * @Date:         2021/3/22 9:39
 * @Description:
 */
@Dao
interface BingImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBingImages(banners: List<BingImage.Image>)

    @Query("SELECT * FROM bing")
    fun getBingImages(): PagingSource<Int, BingImage.Image>

    @Query("DELETE  FROM bing")
    fun deleteAllBingImages()
}