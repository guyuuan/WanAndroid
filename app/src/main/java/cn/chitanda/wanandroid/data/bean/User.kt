package cn.chitanda.wanandroid.data.bean


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("admin")
    val admin: Boolean = false,
    @SerializedName("chapterTops")
    val chapterTops: List<Any> = emptyList(),
    @SerializedName("coinCount")
    val coinCount: Int = 0,
    @SerializedName("collectIds")
    val collectIds: List<Int> = emptyList(),
    @SerializedName("email")
    val email: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("publicName")
    val publicName: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("username")
    val username: String = ""
)