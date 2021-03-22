package cn.chitanda.wanandroid.data.bean


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "banner")
data class Banner(
    @SerializedName("desc")
    val desc: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("imagePath")
    val imagePath: String,
    @SerializedName("isVisible")
    val isVisible: Int,
    @SerializedName("order")
    val order: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("url")
    val url: String
)