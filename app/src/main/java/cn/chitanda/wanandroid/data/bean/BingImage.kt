package cn.chitanda.wanandroid.data.bean


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class BingImage(
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("tooltips")
    val tooltips: Tooltips
) {
    @Entity(tableName = "bing")
    data class Image(
        @PrimaryKey
        @SerializedName("url")
        val url: String,
    ) {
        val imageUrl
            get() = "https://s.cn.bing.net$url"

    }

    data class Tooltips(
        @SerializedName("loading")
        val loading: String,
        @SerializedName("next")
        val next: String,
        @SerializedName("previous")
        val previous: String,
        @SerializedName("walle")
        val walle: String,
        @SerializedName("walls")
        val walls: String
    )
}