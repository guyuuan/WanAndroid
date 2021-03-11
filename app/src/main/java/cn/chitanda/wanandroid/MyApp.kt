package cn.chitanda.wanandroid

import android.app.Application
import com.tencent.mmkv.MMKV

/**
 * @Author:       Chen
 * @Date:         2021/3/11 9:18
 * @Description:
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}