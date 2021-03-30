package cn.chitanda.wanandroid.ui.scenes.home

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import cn.chitanda.wanandroid.config.Constant
import cn.chitanda.wanandroid.data.bean.Article
import cn.chitanda.wanandroid.data.bean.Banner
import cn.chitanda.wanandroid.ui.compose.Center
import cn.chitanda.wanandroid.ui.compose.SwipeToRefreshLayout
import cn.chitanda.wanandroid.utils.px2dp
import cn.chitanda.wanandroid.viewmodel.DataViewModel
import com.tencent.mmkv.MMKV
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author:       Chen
 * @Date:         2021/3/16 15:57
 * @Description:
 */

@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun Articles() {
    val viewModel = viewModel<DataViewModel>()
    val articles = viewModel.articles.collectAsLazyPagingItems()
    val banners = viewModel.banners.collectAsLazyPagingItems()
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BannerList(
                    banners = banners, modifier = Modifier
                        .fillMaxWidth()
                        .weight(2.5f)
                )
                ArticleList(articles, modifier = Modifier.weight(8f))
            }
        }
    }
}

@Composable
fun BannerList(banners: LazyPagingItems<Banner>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(banners) { _, banner ->
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(Resources.getSystem().displayMetrics.widthPixels.px2dp().dp)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
            ) {
                banner?.let {
                    NetworkImage(
                        url = it.imagePath,
                        modifier = Modifier.fillMaxSize(),
                        onLoading = {
                            Center(Modifier.fillMaxSize()) {
                                CircularProgressIndicator()
                            }
                        },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ArticleList(articles: LazyPagingItems<Article.Data>, modifier: Modifier = Modifier) {
    val listState = rememberLazyListState()
    val mmkv = remember {
        MMKV.defaultMMKV()
    }
    LazyColumn(
        modifier = modifier then Modifier.fillMaxSize(),
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

    DisposableEffect(key1 = listState) {
        onDispose {
            mmkv?.encode(Constant.KEY_HOME_ARTICLES_SCROLL_STATUS_POSITION, listState.firstVisibleItemIndex)
            mmkv?.encode(
                Constant.KEY_HOME_ARTICLES_SCROLL_STATUS_OFFSETS,
                listState.firstVisibleItemScrollOffset
            )
        }
    }
    LaunchedEffect(key1 = mmkv) {
        this.launch(Dispatchers.IO) {
            delay(200)
            val scrollPosition = mmkv?.getInt(Constant.KEY_HOME_ARTICLES_SCROLL_STATUS_POSITION, 0) ?: 0
            val offset = mmkv?.getInt(Constant.KEY_HOME_ARTICLES_SCROLL_STATUS_OFFSETS, 0) ?: 0
            withContext(Dispatchers.Main) {
                listState.scrollToItem(scrollPosition, offset)
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