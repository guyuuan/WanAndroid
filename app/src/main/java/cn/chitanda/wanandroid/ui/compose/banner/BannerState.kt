package cn.chitanda.wanandroid.ui.compose.banner

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * @Author:       Chen
 * @Date:         2021/3/19 14:35
 * @Description:
 */
private const val TAG = "BannerState"

//@Composable
//fun rememberBannerState(): BannerState = rememberSaveable(saver = BannerState.Saver) {
//
//}
@Stable
class BannerState(
    @IntRange(from = 0) pageCount: Int,
    @IntRange(from = 0) currentPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) currentPageOffset: Float = 0f
) {
    private var _pageCount by mutableStateOf(pageCount)
    private var _currentPage by mutableStateOf(currentPage)
    private var _currentPageOffset = mutableStateOf(currentPageOffset)
    internal var pageSize by mutableStateOf(0)

    init {
        require(pageCount >= 0) { "pageCount must be >= 0" }
        requireCurrentPage(currentPage, "currentPage")
        requireCurrentPageOffset(currentPageOffset, "currentPageOffset")
    }

    /**
     * The number of pages to display.
     */
    @get:IntRange(from = 0)
    var pageCount: Int
        get() = _pageCount
        set(@IntRange(from = 0) value) {
            require(value >= 0) { "pageCount must be >= 0" }
            _pageCount = value
            currentPage = currentPage.coerceIn(0, lastPageIndex)
        }

    internal val lastPageIndex: Int
        get() = (pageCount - 1).coerceAtLeast(0)

    /**
     * The index of the currently selected page.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    @get:IntRange(from = 0)
    var currentPage: Int
        get() = _currentPage
        private set(value) {
            _currentPage = value.coerceIn(0, lastPageIndex)
        }

    /**
     * The current offset from the start of [currentPage], as a fraction of the page width.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    @get:FloatRange(from = 0.0, to = 1.0)
    var currentPageOffset: Float
        get() = _currentPageOffset.value
        private set(value) {
            _currentPageOffset.value = value.coerceIn(
                minimumValue = 0f,
                maximumValue = if (currentPage == lastPageIndex) 0f else 1f,
            )
        }

    internal val draggableState = DraggableState { delta ->
        dragByOffset(delta / pageSize.coerceAtLeast(1))
    }

    private fun dragByOffset(deltaOffset: Float) {
        val targetOffset = currentPageOffset - deltaOffset

        if (targetOffset < 0) {
            //targetOffset < 0, 将要滑动至上一页
            if (currentPage > 0) {
                currentPage--
                currentPageOffset
            } else {
                //如果当前在在page 0,滑动至最后一页
                currentPage =lastPageIndex
            }
        }
    }


    override fun toString(): String = "PagerState(" +
            "pageCount=$pageCount, " +
            "currentPage=$currentPage, " +
            "currentPageOffset=$currentPageOffset" +
            ")"

    private fun requireCurrentPage(value: Int, name: String) {
        if (pageCount == 0) {
            require(value == 0) { "$name must be 0 when pageCount is 0" }
        } else {
            require(value in 0 until pageCount) {
                "$name must be >= 0 and < pageCount"
            }
        }
    }

    private fun requireCurrentPageOffset(value: Float, name: String) {
        if (pageCount == 0) {
            require(value == 0f) { "$name must be 0f when pageCount is 0" }
        } else {
            require(value in 0f..1f) { "$name must be >= 0 and <= 1" }
        }
    }
//    companion object {
//        val Saver: Saver<BannerState, *> = listSaver(
//            save = { listOf<Any>(it) }
//        )
//    }
}