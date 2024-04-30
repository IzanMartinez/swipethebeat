package com.izamaralv.swipethebeat.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun MainScreen(){
    val state = rememberSwipeableCardState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .swipableCard(
                state = state,
                onSwiped = { direction ->
                    println("The card was swiped to $direction")
                },
                onSwipeCancel = {
                    println("The swiping was cancelled")
                }
            )
    ) {
        // contents ...
    }
}