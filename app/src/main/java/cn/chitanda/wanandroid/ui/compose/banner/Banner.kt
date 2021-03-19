package cn.chitanda.wanandroid.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.chitanda.wanandroid.data.bean.Banner

/**
 * @Author:       Chen
 * @Date:         2021/3/19 11:33
 * @Description:
 */
@Composable
fun Banner(modifier: Modifier = Modifier) {
//    Layout(content = { }, measurePolicy = )
    Row(modifier= modifier
        .width(300.dp)
        .background(Color.White)
        ,horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "1")
        Text(text = "2")
        Text(text = "3")
        Text(text = "4")
    }
    LazyRow() {
        
    }
}

@Preview(showBackground = true)
@Composable
fun BannerPreview(){
    Banner()
}