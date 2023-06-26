package com.kamalbramwell.dating.explore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kamalbramwell.dating.registration.data.StockImageDataSource
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.components.rememberBrandGradient
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.ui.theme.magenta
import com.kamalbramwell.dating.user.model.Seeking
import com.kamalbramwell.dating.user.model.UserData
import com.kamalbramwell.dating.utils.UiText
import java.time.LocalDate
import java.time.Period


@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    user: UserData = UserData.random(),
    cardStackController: CardStackController = rememberCardStackController()
) {
    Column(
        modifier.background(
            color = MaterialTheme.colorScheme.background,
            shape = MaterialTheme.shapes.extraLarge
        )
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .clip(MaterialTheme.shapes.extraLarge)
        ) {
            if (user.photoUrl.isNotEmpty()) {
//            AsyncImage(
//                model = user.photoUrl,
//                contentDescription = "Profile photo of ${user.name}",
//                contentScale = ContentScale.Crop,
//                modifier = modifier.fillMaxSize()
//            )
                val resourceId = remember { StockImageDataSource.random() }
                Image(
                    painter = painterResource(id = resourceId),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Username(name = user.name)
                Age(birthday = user.birthday)
                Seeking(seeking = user.seeking)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NotInterestedButton(onClick = { cardStackController.swipeLeft() })
            Spacer(modifier = Modifier.size(defaultContentPadding))
            LikeButton(onClick = { cardStackController.swipeRight() })
        }
    }
}

@Composable
private fun Username(name: String) {
    DatingText(
        text = UiText.DynamicString(name),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )
}

private fun Long.toAge(): String =
    Period.between(LocalDate.ofEpochDay(this), LocalDate.now()).years.toString()

@Composable
private fun Age(birthday: Long) {
    DatingText(
        text = UiText.DynamicString(birthday.toAge()),
        color = Color.White,
        fontSize = 20.sp,
    )
}

@Composable
fun Seeking(seeking: Seeking) {
    val brush = rememberBrandGradient()

    DatingText(
        text = UiText.StringResource(seeking.label),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .drawBehind {
                drawRoundRect(
                    brush = brush,
                    cornerRadius = CornerRadius(32f, 32f)
                )
            }
            .padding(defaultContentPadding),
        color = Color.White,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun NotInterestedButton(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick, modifier = Modifier.padding(defaultContentPadding)) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "",
            tint = magenta,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        )
    }
}

@Composable
fun LikeButton(onClick: () -> Unit = {}) {
    val brush = rememberBrandGradient()

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .drawBehind {
                drawCircle(brush = brush)
            }
            .padding(defaultContentPadding)
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileCardPreview() {
    DatingTheme {
        ProfileCard()
    }
}

@Preview
@Composable
private fun ProfileCardDarkPreview() {
    DatingTheme(darkTheme = true) {
        ProfileCard()
    }
}