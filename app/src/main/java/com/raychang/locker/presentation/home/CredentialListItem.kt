package com.raychang.locker.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.raychang.locker.R
import com.raychang.locker.domain.model.Credential
import com.raychang.locker.ui.theme.AppStyle

@Composable
fun CredentialListItem(
    modifier: Modifier = Modifier,
    credential: Credential,
    isExpanded: Boolean,
    showPassword: Boolean = false,
    onToggleExpand: () -> Unit,
    onNavigateToDetail: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            // Main row — tap to expand/collapse
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() }
                    .padding(
                        start = AppStyle.Spacing.size16,
                        top = AppStyle.Spacing.size16,
                        bottom = AppStyle.Spacing.size16,
                        end = AppStyle.Spacing.size8
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = credential.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    if (credential.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(AppStyle.Spacing.size2))
                        Text(
                            text = credential.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(onClick = onNavigateToDetail) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.view_detail),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(AppStyle.IconSize.size20)
                    )
                }
            }

            // Expanded peek content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                        .padding(AppStyle.Spacing.size16),
                    verticalArrangement = Arrangement.spacedBy(AppStyle.Spacing.size8)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))

                    // Account row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(AppStyle.IconSize.size16)
                        )
                        Spacer(modifier = Modifier.width(AppStyle.Spacing.size8))
                        Text(
                            text = stringResource(R.string.account_label),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = credential.account,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))

                    // Password row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VpnKey,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(AppStyle.IconSize.size16)
                        )
                        Spacer(modifier = Modifier.width(AppStyle.Spacing.size8))
                        Text(
                            text = stringResource(R.string.password_label),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = if (showPassword) credential.password else "\u2022".repeat(
                            credential.password.length.coerceAtMost(
                                12
                            )
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
