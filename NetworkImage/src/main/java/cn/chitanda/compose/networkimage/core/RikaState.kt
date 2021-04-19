package cn.chitanda.compose.networkimage.core

/**
 * @Author:       Chen
 * @Date:         2021/3/8 15:46
 * @Description:
 */
sealed class RikaState<out T> {
    object Loading : RikaState<Nothing>()

    data class Success<T>(val result: T) : RikaState<T>()
    data class Failure(val error: Throwable) : RikaState<Nothing>()
}