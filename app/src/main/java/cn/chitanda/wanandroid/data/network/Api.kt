package cn.chitanda.wanandroid.data.network

import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.bean.BingImage
import cn.chitanda.wanandroid.data.bean.HttpJson
import cn.chitanda.wanandroid.data.bean.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author:       Chen
 * @Date:         2021/3/10 13:09
 * @Description:
 */
interface Api {
    @POST("/user/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): HttpJson<User>

    @GET("/article/list/{page}/json")
    suspend fun getHomeArticles(@Path("page") page: Int): HttpJson<Article>

    @GET("https://cn.bing.com/HPImageArchive.aspx?format=js&cc=cn&idx=0&n=1")
    suspend fun getTodayImage():BingImage
}