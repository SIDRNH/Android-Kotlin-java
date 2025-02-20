package com.example.password.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpOffset

@Composable
fun <T> DropDownContextMenu(
    expanded: Boolean,
    offset: DpOffset,
    item: T,
    actions: List<Pair<String, (T) -> Unit>>,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        offset = offset
    ) {
        actions.forEach { action ->
            DropdownMenuItem(
                onClick = {action.second(item)},
                text = {Text(text = action.first)}
            )
        }
    }
}