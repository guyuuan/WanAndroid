package cn.chitanda.wanandroid.data.bean


import com.google.gson.annotations.SerializedName

data class BingImage(
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("tooltips")
    val tooltips: Tooltips
) {
    data class Image(
        @SerializedName("bot")
        val bot: Int,
        @SerializedName("copyright")
        val copyright: String,
        @SerializedName("copyrightlink")
        val copyrightlink: String,
        @SerializedName("drk")
        val drk: Int,
        @SerializedName("enddate")
        val enddate: String,
        @SerializedName("fullstartdate")
        val fullstartdate: String,
        @SerializedName("hs")
        val hs: List<Any>,
        @SerializedName("hsh")
        val hsh: String,
        @SerializedName("quiz")
        val quiz: String,
        @SerializedName("startdate")
        val startdate: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("top")
        val top: Int,
        @SerializedName("url")
        val url: String,
        @SerializedName("urlbase")
        val urlbase: String,
        @SerializedName("wp")
        val wp: Boolean
    )

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