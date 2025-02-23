package com.example.retrofitpractice.authentication.presentation.components

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.retrofitpractice.ui.theme.RetrofitPracticeTheme

@Composable
fun OtpInputField(
    modifier: Modifier = Modifier,
    number: Int?,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit
) {
    val text by remember { mutableStateOf(
        TextFieldValue(
            text = number?.toString().orEmpty(),
            selection = TextRange(
                index = if (number != null) 1 else 0
            )
        )
    )};

    var isFocused by remember { mutableStateOf(false) };

    Box(
        modifier = modifier
            .border(width = 1.dp, color = Color(0xff00f15e))
            .background(color = Color(0xff262626)),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier
                .padding(10.dp)
                .focusRequester(focusRequester).onKeyEvent { event ->
                    val delPressed = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if (delPressed && number == null) onKeyboardBack();
                    false
                }.onFocusChanged {
                    isFocused = it.isFocused;
                    onFocusChanged(it.isFocused);
                },
            value = text,
            onValueChange = { newText ->
                val newNumber = newText.text
                if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChanged(newNumber.toIntOrNull())
                }
            },
            cursorBrush = SolidColor(Color(0xff00f15e)),
            singleLine = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                fontSize = 36.sp,
                color = Color(0xff00f15e)
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

@Preview
@Composable
private fun PreviewOtpInputField() {
    RetrofitPracticeTheme {
        OtpInputField(
            number = null,
            focusRequester = remember { FocusRequester() },
            onFocusChanged = {},
            onKeyboardBack = {},
            onNumberChanged = {},
            modifier = Modifier.size(100.dp)
        );
    }
}