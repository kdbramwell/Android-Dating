package com.kamalbramwell.dating.explore.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kamalbramwell.dating.explore.ui.model.Item


@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    item: Item,
    cardStackController: CardStackController
) {
    Box(modifier = modifier) {
        if (item.url != null) {
            AsyncImage(
                model = item.url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = modifier.fillMaxSize()
            )
        }

        Column(
            modifier = modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            Text(text = item.text, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)

            Text(text = item.subText, color = Color.White, fontSize = 20.sp)

            Row {
                IconButton(
                    modifier = modifier.padding(50.dp, 0.dp, 0.dp, 0.dp),
                    onClick = { cardStackController.swipeLeft() },
                ) {
                    Icon(
                        Icons.Default.Close, contentDescription = "", tint = Color.White, modifier =
                        modifier
                            .height(50.dp)
                            .width(50.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = modifier.padding(0.dp, 0.dp, 50.dp, 0.dp),
                    onClick = { cardStackController.swipeRight() }
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder, contentDescription = "", tint = Color.White, modifier =
                        modifier.height(50.dp).width(50.dp)
                    )
                }
            }
        }
    }
}