package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import cn.chitanda.wanandroid.R
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.ui.compose.LocalStatusBaeHeight
import cn.chitanda.wanandroid.viewmodel.ArticleViewModel

/**
 * @Author:       Chen
 * @Date:         2021/3/10 11:43
 * @Description:
 */
@Composable
fun HomeScene() {
    val statusBarHeight = LocalStatusBaeHeight.current
    val viewModel = viewModel<ArticleViewModel>()
    val articles by viewModel.articles.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Scaffold(modifier = Modifier
            .padding(top = statusBarHeight)
            .fillMaxSize(), topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        }) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(articles) { article ->
                    Text(text = article.title)
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article.Data) {

}

