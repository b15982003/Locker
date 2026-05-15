package com.raychang.locker.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.raychang.locker.ui.theme.AppStyle

@Composable
fun TagSectionHeader(
    modifier: Modifier = Modifier,
    tagName: String
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size8),
        text = tagName,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary
    )
}
