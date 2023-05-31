package com.kamalbramwell.dating.utils

import android.util.Patterns

fun CharSequence.isPhoneNumber(): Boolean = Patterns.PHONE.matcher(this).matches()

fun CharSequence.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()