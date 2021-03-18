package cn.chitanda.wanandroid.ui.scenes.home

import android.content.res.Resources
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import cn.chitanda.wanandroid.ui.compose.SwipeToRefreshLayout
import cn.chitanda.wanandroid.utils.px2dp
import cn.chitanda.wanandroid.viewmodel.ArticleViewModel
import dev.chrisbanes.accompanist.insets.statusBarsPadding

/**
 * @Author:       Chen
 * @Date:         2021/3/16 15:57
 * @Description:
 */

@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun Articles() {
    val viewModel = viewModel<ArticleViewModel>()
    val articles = viewModel.articles.collectAsLazyPagingItems()

    Scaffold(modifier = Modifier
        .statusBarsPadding()
        .fillMaxSize(), topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
    }) {
        Image(
            painter = painterResource(id = R.drawable.ic_jetpack), contentDescription = "",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Fit
        )

        SwipeToRefreshLayout(
            refreshingState = articles.loadState.refresh is LoadState.Loading,
            onRefresh = { articles.refresh() },
            refreshIndicator = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(elevation = 4.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.surface),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                    )
                }
            }) {
            ArticleList(articles)
        }
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
    var start by remember {
        mutableStateOf(false)
    }
    val width = Resources.getSystem().displayMetrics.widthPixels
    val alpha by animateFloatAsState(targetValue = if (start) 1f else 0.7f)
    val offsetX by animateIntAsState(targetValue = if (start) 0 else -width.px2dp() / 2)
    val scale by animateFloatAsState(targetValue = if (start) 1f else 1.2f)
    Card(
        modifier = Modifier
            .graphicsLayer {
                translationX = offsetX.dp.toPx()
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = if (position == 0) 12.dp else 8.dp,
                bottom = 8.dp
            )
            .fillMaxWidth()
            .onSizeChanged { start = true },
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
    SideEffect {
//        start = true
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