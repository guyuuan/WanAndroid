package cn.chitanda.wanandroid.ui.scenes.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cn.chitanda.wanandroid.R
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

/**
 * @Author:       Chen
 * @Date:         2021/3/16 16:12
 * @Description:
 */

sealed class Tab(@DrawableRes val icon: Int, val label: String) {
    object Home : Tab(R.drawable.ic_home, "Home")
    object Project : Tab(R.drawable.ic_project_sort, "Project")
    object Explore : Tab(R.drawable.ic_explore, "Explore")
    object Me : Tab(R.drawable.ic_me, "Me")
}

@Composable
fun BottomNavBar(currentTab: Tab, onClick: (Tab) -> Unit) {
    BottomNavigation {
        Tab::class.sealedSubclasses.forEach { tab ->
            tab.objectInstance?.let {
                BottomNavigationItem(
                    selected = it == currentTab,
                    onClick = {
                        onClick(it)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(text = it.label) },
                    alwaysShowLabel = false
                    )
            }
        }
    }
}

