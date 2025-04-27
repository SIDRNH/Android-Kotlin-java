package com.example.yeschat.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSelectionDialog(
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Row {
                Button(
                    onClick = onCameraSelected,
                    shape = RoundedCornerShape(3.dp),
                    content = { Text(text = "Camera", color = Color.White) }
                );
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = onGallerySelected,
                    shape = RoundedCornerShape(3.dp),
                    content = { Text(text = "Gallery", color = Color.White) }
                );
            }
        },
        title = { Text(text = "Pick a Source") },
        text = {Text(text = "Would you like to use Gallery or Camera to Send") },
    );
}