package com.example.password.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

data class DropDownItem(
    val text: String
)

@Composable
fun Item(
    itemName: String,
    dropDownItems: List<DropDownItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DropDownItem) -> Unit,
    onClick: (() -> Unit)? = null
) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) };
    var pressOffset by remember {mutableStateOf(DpOffset.Zero)};
    var itemHeight by remember { mutableStateOf(0.dp) };
    val density = LocalDensity.current;
    val borderColor: Color = if (isSystemInDarkTheme()) Color.White else Color.Black;

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier.onSizeChanged {
            itemHeight = with(density) {it.height.toDp()}
        }.border(1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(size = 100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = {
                            onClick?.invoke();
                        },
                        onLongPress = {
                            isContextMenuVisible = true;
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp());
                        }
                    )
                }
                .padding(16.dp)
        ) {
            Text(text = itemName)
        }
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = {isContextMenuVisible = false},
            offset = pressOffset.copy(y = pressOffset.y - itemHeight)
        ) {
            dropDownItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.text) },
                    onClick = {
                        onItemClick(item);
                        isContextMenuVisible = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropDown() {
    Item(
        itemName = "SIDR",
        dropDownItems = listOf<DropDownItem>(DropDownItem(text = "Delete"), DropDownItem(text = "Edit")),
        onItemClick = {}
    );
}