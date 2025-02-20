package com.example.password.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.password.objects.category.CategoryEvent
import com.example.password.objects.category.CategoryState
import com.example.password.objects.item.ItemEvent
import com.example.password.objects.item.ItemState
import com.example.password.ui.theme.Black
import com.example.password.ui.theme.PasswordTheme
import com.example.password.ui.theme.White

@Composable
fun AddCategory(state: CategoryState, onEvent: (CategoryEvent) -> Unit, modifier: Modifier = Modifier) {
    val uiTextColors = if (isSystemInDarkTheme()) White else Black;
    PasswordTheme {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onEvent(CategoryEvent.HideDialog)
            },
            title = {
                Text(
                    text = "Add Category"
                )
            },
            text = {
                OutlinedTextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(CategoryEvent.SetName(it))
                    },
                    label = {
                        Text(text = "Name")
                    }
                )
            },
            confirmButton = {
                Button(
                    enabled = state.name.isNotBlank(),
                    onClick = { onEvent(CategoryEvent.SaveCategory) },
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Save", color = uiTextColors);
                }
            },
            dismissButton = {
                Button(
                    onClick = { onEvent(CategoryEvent.HideDialog) },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = "Cancel", color = uiTextColors);
                }
            },
            shape = RoundedCornerShape(5.dp)
        )
    }
}

@Composable
fun AddItem(state: ItemState, onEvent: (ItemEvent) -> Unit, modifier: Modifier = Modifier) {
    val uiTextColors = if (isSystemInDarkTheme()) White else Black;
    var isPasswordVisible by remember { mutableStateOf(false) };
    PasswordTheme {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onEvent(ItemEvent.HideDialog)
            },
            title = {
                Text(
                    text = "Add Category"
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = {
                            onEvent(ItemEvent.SetName(it))
                        },
                        label = {
                            Text(text = "Name")
                        }
                    );
                    Spacer(modifier = Modifier.height(8.dp));
                    OutlinedTextField(
                        value = state.userName,
                        onValueChange = {
                            onEvent(ItemEvent.SetUserName(it))
                        },
                        label = {
                            Text(text = "Username/Email")
                        }
                    );
                    Spacer(modifier = Modifier.height(8.dp));
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = {
                            onEvent(ItemEvent.SetPassword(it))
                        },
                        label = {
                            Text(text = "Password")
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    isPasswordVisible = !isPasswordVisible;
                                },
                                content = {
                                    Text(text = if (isPasswordVisible) "Hide" else "Show")
                                }
                            )
                        },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp));
                    OutlinedTextField(
                        value = state.webLink,
                        onValueChange = {
                            onEvent(ItemEvent.SetWebLink(it))
                        },
                        label = {
                            Text(text = "WebSite")
                        }
                    );
                    Spacer(modifier = Modifier.height(8.dp));
                    OutlinedTextField(
                        value = state.info,
                        onValueChange = {
                            onEvent(ItemEvent.SetInfo(it))
                        },
                        label = {
                            Text(text = "Info")
                        },
                        placeholder = {
                            Text(text = "Info About the Account")
                        },
                        maxLines = 3,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Default
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    enabled = state.name.isNotBlank(),
                    onClick = { onEvent(ItemEvent.SaveItem) },
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(text = "Save", color = uiTextColors);
                }
            },
            dismissButton = {
                Button(
                    onClick = { onEvent(ItemEvent.HideDialog) },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = "Cancel", color = uiTextColors);
                }
            },
            shape = RoundedCornerShape(5.dp)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAddCategory() {
    AddItem(state = ItemState(), onEvent = {})
}