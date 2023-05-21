package com.kamalbramwell.dating.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kamalbramwell.dating.utils.UiText


@Composable
fun InputField(
    textFieldValue: TextFieldValue,
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onTextFieldFocused: (Boolean) -> Unit = {},
    error: UiText? = null,
    label: UiText? = null,
    placeholder: UiText? = null,
    icon: (@Composable () -> Unit)? = null,
    onIconClick: () -> Unit = {},
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences
    ),
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    hideKeyboard: Boolean = false,
    onFocusClear: () -> Unit = {}
) {

    val borderColor = error?.let {
        MaterialTheme.colorScheme.error
    } ?: MaterialTheme.colorScheme.outline

    val labelColor = error?.let {
        MaterialTheme.colorScheme.error
    } ?: MaterialTheme.colorScheme.outline

    val focusedBorderColor = error?.let {
        MaterialTheme.colorScheme.error
    } ?: MaterialTheme.colorScheme.primary

    OutlinedTextInput(
        textFieldValue = textFieldValue,
        modifier = modifier,
        onTextChanged = onTextChanged,
        onTextFieldFocused = onTextFieldFocused,
        label = error ?: label,
        placeholder = placeholder,
        icon = icon,
        onIconClick = onIconClick,
        enabled = enabled,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        labelColor = labelColor,
        placeholderColor = placeholderColor,
        borderColor = borderColor,
        focusedBorderColor = focusedBorderColor,
        backgroundColor = backgroundColor,
        hideKeyboard = hideKeyboard,
        onFocusClear = onFocusClear
    )
}

@Composable
private fun OutlinedTextInput(
    textFieldValue: TextFieldValue,
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onTextFieldFocused: (Boolean) -> Unit = {},
    label: UiText? = null,
    placeholder: UiText? = null,
    icon: (@Composable () -> Unit)? = null,
    onIconClick: () -> Unit = {},
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences
    ),
    labelColor: Color = MaterialTheme.colorScheme.outline,
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    hideKeyboard: Boolean = false,
    onFocusClear: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var lastFocusState by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(64.dp),
                width = 1.dp,
                color = if (lastFocusState) focusedBorderColor else borderColor

            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(64.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            label?.let {
                if (lastFocusState) { // Hidden when not focused
                    DatingText(
                        text = label,
                        color = labelColor,
                        fontSize = 12.sp
                    )
                }
            }

            Box {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = onTextChanged,
                    enabled = enabled,
                    textStyle = LocalTextStyle.current.copy(
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    keyboardOptions = keyboardOptions,
                    singleLine = singleLine,
                    modifier = Modifier
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                onTextFieldFocused(state.isFocused)
                            }
                            lastFocusState = state.isFocused
                        }
                )

                if (textFieldValue.text.isEmpty()) {
                    placeholder?.let { placeholder ->
                        label?.let { label ->
                            DatingText(
                                text = if (!lastFocusState) label else placeholder,
                                color = placeholderColor
                            )
                        }
                    }
                }
            }
        }

        icon?.let {
            IconButton(onClick = onIconClick) {
                icon()
            }
        }
    }

    if (hideKeyboard) {
        focusManager.clearFocus()
        onFocusClear()
    }
}