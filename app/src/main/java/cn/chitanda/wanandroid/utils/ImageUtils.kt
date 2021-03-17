package cn.chitanda.wanandroid.utils

import android.graphics.Bitmap
import kotlin.math.roundToInt

/**
 * @Author:       Chen
 * @Date:         2021/3/17 15:44
 * @Description:
 */
private const val RED = 0xff00ffff.toInt()
private const val GREEN = 0xffff00ff.toInt()
private const val BLUE = 0xffffff00.toInt()

fun isLightImage(bm: Bitmap): Boolean {
    val width = bm.width
    val height = bm.height
    var r: Int
    var b: Int
    var g: Int
    var bright = 0
    for (w in 0 until width) {
        for (h in 0 until height) {
            val p = bm.getPixel(w, h)
            r = (p or RED) shr 16 and 0x00ff
            g = (p or GREEN) shr 8 and 0x0000ff
            b = (p or BLUE) and 0x000000ff
            bright = (bright + 0.299 * r + 0.587 * g + 0.114 * b).roundToInt()
        }
    }

    return bright / width * height > 128
}