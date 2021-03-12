package cn.chitanda.wanandroid.data.bean


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Article(
    val curPage: Int = 0,
    val datas: List<Data> = emptyList(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = -1
) {
    @Entity(tableName = "article")
    data class Data(
//        @SerializedName("apkLink")
        @ColumnInfo(name = "apkLink")
        var apkLink: String = "",
//        @SerializedName("audit")
        @ColumnInfo(name = "audit")
        var audit: Int = 0,
//        @SerializedName("author")
        @ColumnInfo(name = "author")
        var author: String = "",
//        @SerializedName("canEdit")
        @ColumnInfo(name = "canEdit")
        var canEdit: Boolean = false,
//        @SerializedName("chapterId")
        @ColumnInfo(name = "chapterId")
        var chapterId: Int = 0,
//        @SerializedName("chapterName")
        @ColumnInfo(name = "chapterName")
        var chapterName: String = "",
//        @SerializedName("collect")
        @ColumnInfo(name = "collect")
        var collect: Boolean = false,
//        @SerializedName("courseId")
        @ColumnInfo(name = "courseId")
        var courseId: Int = 0,
//        @SerializedName("desc")
        @ColumnInfo(name = "desc")
        var desc: String = "",
//        @SerializedName("descMd")
        @ColumnInfo(name = "descMd")
        var descMd: String = "",
//        @SerializedName("envelopePic")
        @ColumnInfo(name = "envelopePic")
        var envelopePic: String = "",
//        @SerializedName("fresh")
        @ColumnInfo(name = "fresh")
        var fresh: Boolean = false,
//        @SerializedName("host")
        @ColumnInfo(name = "host")
        var host: String = "",
//        @SerializedName("id")
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Int = 0,
//        @SerializedName("link")
        @ColumnInfo(name = "link")
        var link: String = "",
//        @SerializedName("niceDate")
        @ColumnInfo(name = "niceDate")
        var niceDate: String = "",
//        @SerializedName("niceShareDate")
        @ColumnInfo(name = "niceShareDate")
        var niceShareDate: String = "",
//        @SerializedName("origin")
        @ColumnInfo(name = "origin")
        var origin: String = "",
//        @SerializedName("prefix")
        @ColumnInfo(name = "prefix")
        var prefix: String = "",
//        @SerializedName("projectLink")
        @ColumnInfo(name = "projectLink")
        var projectLink: String = "",
//        @SerializedName("publishTime")
        @ColumnInfo(name = "publishTime")
        var publishTime: Long = 0,
//        @SerializedName("realSuperChapterId")
        @ColumnInfo(name = "realSuperChapterId")
        var realSuperChapterId: Int = 0,
//        @SerializedName("selfVisible")
        @ColumnInfo(name = "selfVisible")
        var selfVisible: Int = 0,
//        @SerializedName("shareDate")
        @ColumnInfo(name = "shareDate")
        var shareDate: Long = 0,
//        @SerializedName("shareUser")
        @ColumnInfo(name = "shareUser")
        var shareUser: String = "",
//        @SerializedName("superChapterId")
        @ColumnInfo(name = "superChapterId")
        var superChapterId: Int = 0,
//        @SerializedName("superChapterName")
        @ColumnInfo(name = "superChapterName")
        var superChapterName: String = "",
//        @SerializedName("tags")
        @Ignore
        var tags: List<Tag> = emptyList(),
//        @SerializedName("title")
        @ColumnInfo(name = "title")
        var title: String = "",
//        @SerializedName("type")
        @ColumnInfo(name = "type")
        var type: Int = 0,
//        @SerializedName("userId")
        @ColumnInfo(name = "userId")
        var userId: Int = 0,
//        @SerializedName("visible")
        @ColumnInfo(name = "visible")
        var visible: Int = 0,
//        @SerializedName("zan")
        @ColumnInfo(name = "zan")
        var zan: Int = 0
    ) {
        data class Tag(
            val name: String = "",
            @SerializedName("url")
            val url: String = ""
        )
    }
}
