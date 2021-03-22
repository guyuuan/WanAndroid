package cn.chitanda.wanandroid.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cn.chitanda.wanandroid.data.bean.Banner

/**
 * @Author:       Chen
 * @Date:         2021/3/22 9:39
 * @Description:
 */
@Dao
interface BannerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanners(banners: List<Banner>)

    @Query("SELECT * FROM banner")
    fun getBanners(): PagingSource<Int, Banner>

    @Query("DELETE  FROM banner")
    fun deleteAllBanners()
}