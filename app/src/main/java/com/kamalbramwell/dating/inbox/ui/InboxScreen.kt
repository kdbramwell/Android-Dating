package com.kamalbramwell.dating.inbox.ui

import android.text.format.DateUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.inbox.data.matches
import com.kamalbramwell.dating.inbox.data.randomChats
import com.kamalbramwell.dating.inbox.model.Chat
import com.kamalbramwell.dating.registration.data.StockImageDataSource
import com.kamalbramwell.dating.ui.components.AppBarTitle
import com.kamalbramwell.dating.ui.components.DatingText
import com.kamalbramwell.dating.ui.theme.DatingTheme
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.user.model.UserData
import com.kamalbramwell.dating.utils.UiText

@Composable
fun InboxScreen() {
    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppBarTitle(text = UiText.StringResource(R.string.matches))

        Matches(matches)

        Divider(
            Modifier
                .padding(vertical = defaultContentPadding * 2)
                .fillMaxWidth(0.5f)
        )

        Box(Modifier.weight(1f)) {
            Chats(randomChats)
        }
    }
}

@Composable
private fun Matches(
    users: List<UserData>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onClick: (UserData) -> Unit = {},
) {
    if (users.isEmpty()) {
        Box(
            modifier = Modifier.height(64.dp),
            contentAlignment = Alignment.Center
        ) {
            DatingText(
                UiText.DynamicString("No recent matches"),
                modifier = modifier.padding(defaultContentPadding),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyRow(
            state = listState,
            modifier = modifier.padding(vertical = defaultContentPadding),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(defaultContentPadding)
        ) {

            items(users) {
                Spacer(Modifier.size(defaultContentPadding))

                MatchedUserItem(
                    it,
                    modifier = Modifier.shadow(elevation = defaultContentPadding, shape = CircleShape),
                    onClick = { onClick(it) }
                )

                Spacer(Modifier.size(defaultContentPadding))
            }
        }
    }
}

@Composable
private fun MatchedUserItem(
    userData: UserData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
//    AsyncImage(
//        model = userData.photoUrl,
//        contentDescription = "Profile photo of ${userData.name}",
//        contentScale = ContentScale.Crop,
//        modifier = modifier.fillMaxSize()
//    )
    val resourceId = remember { StockImageDataSource.random() }
    Image(
        painter = painterResource(id = resourceId),
        contentDescription = null,
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun Chats(
    chats: List<Chat>,
    modifier: Modifier = Modifier,
    onClick: (Chat) -> Unit = {},
    scrollState: LazyListState = rememberLazyListState()
) {
    if (chats.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DatingText(text = UiText.DynamicString("No active chats"))
        }
    } else {
        LazyColumn(
            state = scrollState,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
        ) {
            items(chats) {
                ChatItem(
                    chat = it,
                    onClick = { onClick(it) }
                )
            }
        }
    }
}

@Composable
private fun ChatItem(
    chat: Chat,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(modifier.padding(defaultContentPadding)) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = MaterialTheme.shapes.large,
            contentPadding = PaddingValues(defaultContentPadding),
            onClick = onClick
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MatchedUserItem(
                    userData = chat.user,
                    modifier = Modifier.shadow(
                        elevation = defaultContentPadding,
                        shape = CircleShape
                    )
                )

                Spacer(Modifier.size(16.dp))

                Column(Modifier.weight(1f)) {
                    ChatName(chat)
                    LastMessageContent(chat)
                    LastMessageTime(chat)
                }
            }
        }
    }
}

@Composable
private fun ChatName(chat: Chat, modifier: Modifier = Modifier) {
    DatingText(
        text = UiText.DynamicString(chat.user.name),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
}

@Composable
private fun LastMessageContent(chat: Chat, modifier: Modifier = Modifier) {
    DatingText(
        text = UiText.DynamicString(chat.lastMessage),
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        maxLines = 1
    )
}

@Composable
private fun LastMessageTime(chat: Chat, modifier: Modifier = Modifier) {
    val dateString = DateUtils.getRelativeTimeSpanString(
        chat.timestamp,
        System.currentTimeMillis(),
        0L,
        DateUtils.FORMAT_ABBREV_ALL
    ).toString()
    DatingText(
        text = UiText.DynamicString(dateString),
        color = MaterialTheme.colorScheme.outline,
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.End,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun InboxScreenPreview() {
    DatingTheme {
        InboxScreen()
    }
}