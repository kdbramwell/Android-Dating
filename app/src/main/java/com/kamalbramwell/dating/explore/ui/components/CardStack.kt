package com.kamalbramwell.dating.explore.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ThresholdConfig
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kamalbramwell.dating.ui.theme.defaultContentPadding
import com.kamalbramwell.dating.user.model.UserData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardStack(
    modifier: Modifier = Modifier,
    items: MutableList<UserData>,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    velocityThreshold: Dp = 125.dp,
    onSwipeLeft: (userData: UserData) -> Unit = {},
    onSwipeRight: (userData: UserData) -> Unit = {},
    onEmptyStack: (userData: UserData) -> Unit = {}
) {
    var i by remember {
        mutableStateOf(items.size - 1)
    }

    if (i == -1) {
        onEmptyStack(items.last())
    }

    val cardStackController = rememberCardStackController()

    cardStackController.onSwipeLeft = {
        onSwipeLeft(items[i])
        i--
    }

    cardStackController.onSwipeRight = {
        onSwipeRight(items[i])
        i--
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        val stack = createRef()

        Box(
            modifier = modifier
                .constrainAs(stack) {
                    top.linkTo(parent.top)
                }
                .draggableStack(
                    controller = cardStackController,
                    thresholdConfig = thresholdConfig,
                    velocityThreshold = velocityThreshold
                )
                .fillMaxHeight()
        ) {
            items.asReversed().forEachIndexed { index, user ->
                ProfileCard(
                    modifier = Modifier
                        .moveTo(
                            x = if (index == i) cardStackController.offsetX.value else 0f,
                            y = if (index == i) cardStackController.offsetY.value else 0f
                        )
                        .visible(visible = index == i || index == i -1)
                        .shadow(
                            elevation = defaultContentPadding,
                            shape = MaterialTheme.shapes.extraLarge
                        )
                        .graphicsLayer(
                            rotationZ = if (index == i) cardStackController.rotation.value else 0f
                        )
                    ,
                    user,
                    cardStackController
                )
            }
        }
    }
}