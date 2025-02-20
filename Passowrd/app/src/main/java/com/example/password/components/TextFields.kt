package com.example.password.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.password.ui.theme.Black
import com.example.password.ui.theme.focusedTextFieldText
import com.example.password.ui.theme.textFieldContainer
import com.example.password.ui.theme.unfocusedTextFieldText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFields(
    modifier: Modifier = Modifier,
    label: String,
    trailing: String,
    value: String,
    onValueChange: (String) -> Unit,
    password: Boolean = false
) {
    val uiColor: Color = if (isSystemInDarkTheme()) Color.White else Black;
    var show by remember { mutableStateOf(false); }
    OutlinedTextField(
        modifier = modifier,
        label = {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = uiColor);
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.unfocusedTextFieldText,
            focusedTextColor = MaterialTheme.colorScheme.focusedTextFieldText,
            unfocusedContainerColor = MaterialTheme.colorScheme.textFieldContainer,
            focusedContainerColor = MaterialTheme.colorScheme.textFieldContainer
        ),
        trailingIcon = {
            TextButton(
                enabled = !label.isEmpty(),
                onClick = {
                    if (password == true) {
                        show = !show;
                        return@TextButton
                    }
                }
            ) {
                Text(
                    text = if (!show) trailing else "Hide",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                    color = uiColor
                )
            }
        },
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (!show && password == true) PasswordVisualTransformation() else VisualTransformation.None
    );
}

@Preview
@Composable
fun Preview() {
    TextFields(
        label = "Password",
        trailing = "Show",
        password = true,
        value = "",
        onValueChange = {}
    )
}