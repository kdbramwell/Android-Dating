package com.kamalbramwell.dating.registration.data

import androidx.annotation.DrawableRes
import com.kamalbramwell.dating.R

object StockImageDataSource {
    private val photoIds = setOf(
        R.drawable.couple1,
        R.drawable.couple2,
//        R.drawable.couple3,
        R.drawable.man1,
        R.drawable.man2,
//        R.drawable.man3,
//        R.drawable.woman1,
        R.drawable.woman2,
        R.drawable.woman3
    )

    @DrawableRes
    fun random(): Int = photoIds.random()
}