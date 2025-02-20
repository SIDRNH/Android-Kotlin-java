package com.example.password.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.password.ui.theme.LightBlueWhite

@Composable
fun SocialMediaAuth(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Image(painter = painterResource(id = icon),
        contentDescription = null,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .socialMedia()
            .clickable{ onClick() }
            .height(40.dp)
            .padding(5.dp)
    );
}

fun Modifier.socialMedia(): Modifier = composed{
    if (isSystemInDarkTheme()) background(Color.Transparent) else background(LightBlueWhite);
}