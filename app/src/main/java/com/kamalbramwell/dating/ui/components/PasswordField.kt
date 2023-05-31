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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kamalbramwell.dating.R
import com.kamalbramwell.dating.utils.UiText

const val PasswordFieldLabelTestTag = "PasswordFieldLabel"
const val PasswordFieldTestTag = "PasswordField"
const val PasswordFieldTextFieldTestTag = "PasswordFieldTextField"
const val PasswordFieldPlaceholderTestTag = "PasswordFieldPlaceholder"

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onTextFieldFocused: (Boolean) -> Unit = {},
    label: UiText,
    placeholder: UiText,
    error: UiText? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions(),
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

    SensitiveOutlinedTextInput(
        textFieldValue = textFieldValue,
        onTextChanged = onTextChanged,
        onTextFieldFocused = onTextFieldFocused,
        label = error ?: label,
        placeholder = placeholder,
        labelColor = labelColor,
        focusedBorderColor = focusedBorderColor,
        backgroundColor = MaterialTheme.colorScheme.background,
        borderColor = borderColor,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
private fun SensitiveOutlinedTextInput(
    textFieldValue: TextFieldValue,
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit = {},
    onTextFieldFocused: (Boolean) -> Unit = {},
    label: UiText? = null,
    placeholder: UiText? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions(),
    labelColor: Color = MaterialTheme.colorScheme.outline,
    placeholderColor: Color = MaterialTheme.colorScheme.outline,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {
    var lastFocusState by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }

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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .semantics { testTag = PasswordFieldTestTag },
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
                        fontSize = 12.sp,
                        modifier = Modifier.semantics { testTag = PasswordFieldLabelTestTag }
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
                    keyboardActions = keyboardActions,
                    singleLine = singleLine,
                    modifier = Modifier
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                onTextFieldFocused(state.isFocused)
                            }
                            lastFocusState = state.isFocused
                        }.semantics {
                            testTag = PasswordFieldTextFieldTestTag
                        },
                    visualTransformation =
                    if (textVisible) VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                if (textFieldValue.text.isEmpty()) {
                    placeholder?.let { placeholder ->
                        label?.let { label ->
                            DatingText(
                                text = if (!lastFocusState) label else placeholder,
                                color = placeholderColor,
                                modifier = Modifier.semantics {
                                    testTag = PasswordFieldPlaceholderTestTag
                                }
                            )
                        }
                    }
                }
            }
        }

        IconButton(
            onClick = { textVisible = !textVisible }
        ) {
            val image =
                if (textVisible) Icons.Outlined.Visibility
                else Icons.Outlined.VisibilityOff

            val description = stringResource(
                if (textVisible) R.string.registration_a11y_hide_password
                else R.string.registration_a11y_show_password
            )

            Icon(
                imageVector = image,
                contentDescription = description,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}
