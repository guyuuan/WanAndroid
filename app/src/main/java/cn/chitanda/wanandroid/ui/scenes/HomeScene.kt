package cn.chitanda.wanandroid.ui.scenes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import cn.chitanda.compose.networkimage.core.NetworkImage
import cn.chitanda.wanandroid.R
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.LocalSystemBar
import cn.chitanda.wanandroid.viewmodel.ArticleViewModel

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
    val viewModel = viewModel<ArticleViewModel>()
    val articles = viewModel.articles.collectAsLazyPagingItems()

    Scaffold(modifier = Modifier
        .padding(top = systemBar.first, bottom = systemBar.second)
        .fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
    }) {
        Image(
            painter = painterResource(id = R.drawable.ic_jetpack), contentDescription = "",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit
        )
        ArticleList(articles)
    }
}

@ExperimentalMaterialApi
@Composable
fun ArticleList(articles: LazyPagingItems<Article.Data>) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
    ) {
        itemsIndexed(articles) { position, article ->
            article?.let {
                ArticleItem(article = it, position = position)
            }
        }
        with(articles) {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        LoadingView(Modifier.fillMaxSize())
                    }
                }
                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                }
                else -> {
                }
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article.Data, position: Int) {
    Card(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = if (position == 0) 12.dp else 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row {
            if (article.envelopePic.isNotEmpty() && article.envelopePic.startsWith("http")) {
                NetworkImage(url = article.envelopePic,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .background(
                            Color(0xFF073042)
                        )
                        .size(95.dp)
                        .clip(MaterialTheme.shapes.medium), onLoading = {
                        Center(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator()
                        }
                    }, onFailure = {
                        NoEnvelopePic()
                    })
            } else {
                NoEnvelopePic()
            }
            Column(
                modifier = Modifier
                    .height(95.dp)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                //文章标题
                Text(
                    text = article.title,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                //
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //分类
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp)),
                        color = MaterialTheme.colors.primary
                    ) {
                        Text(
                            text = article.superChapterName,
                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    //作者
                    Text(
                        text = if (article.author.isEmpty()) article.shareUser else article.author,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        }
    }
}

@Composable
fun NoEnvelopePic() {
    Image(
        painter = painterResource(id = R.drawable.ic_jetpack),
        contentDescription = "",
        modifier = Modifier
            .background(
                Color(0xFF073042)
            )
            .size(95.dp)
            .clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Center(modifier) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun ArticleItemPreview() {
    ArticleItem(
        article = Article.Data(
            title = "asdadada",
            author = "111",
            superChapterName = "jetpack"
        ), position = 0
    )
}
