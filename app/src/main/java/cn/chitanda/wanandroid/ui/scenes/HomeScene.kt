package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.ExperimentalPagingApi
import cn.chitanda.wanandroid.ui.compose.LocalSystemBar
import cn.chitanda.wanandroid.ui.scenes.home.Articles
import cn.chitanda.wanandroid.ui.scenes.home.BottomNavBar
import cn.chitanda.wanandroid.ui.scenes.home.Explore
import cn.chitanda.wanandroid.ui.scenes.home.Me
import cn.chitanda.wanandroid.ui.scenes.home.Project
import cn.chitanda.wanandroid.ui.scenes.home.Tab

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:43
 * @Description:
 */
@ExperimentalPagingApi
@ExperimentalMaterialApi
@Composable
fun HomeScene() {
    val systemBar = LocalSystemBar.current
    var currentTab by remember { mutableStateOf<Tab>(Tab.Home) }
    Scaffold(
        modifier = Modifier
            .padding(bottom = systemBar.second)
            .fillMaxSize(),
        bottomBar = {
            BottomNavBar(currentTab = currentTab) { tab ->
                currentTab = tab
            }
        }
    ) {
        Crossfade(targetState = currentTab) { tab ->
            when (tab) {
                is Tab.Home -> Articles()
                is Tab.Explore -> Explore()
                is Tab.Project -> Project()
                is Tab.Me -> Me()
            }
        }
    }
}


