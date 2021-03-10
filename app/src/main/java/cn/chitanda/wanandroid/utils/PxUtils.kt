package cn.chitanda.wanandroid.utils

import android.content.res.Resources

/**
 * @Author:       Chen
 * @Date:         2021/3/9 15:07
 * @Description:
 */

val density = Resources.getSystem().displayMetrics.density
fun Int.px2dp(): Int {
    return (this / density + 0.5f).toInt()
}