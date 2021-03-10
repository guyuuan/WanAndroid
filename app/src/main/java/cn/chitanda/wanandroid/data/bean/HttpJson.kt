package cn.chitanda.wanandroid.data.bean


import com.google.gson.annotations.SerializedName

data class HttpJson<T>(
    @SerializedName("data")
    val `data`: T? = null,
    @SerializedName("errorCode")
    val errorCode: Int = 0,
    @SerializedName("errorMsg")
    val errorMsg: String = ""
)