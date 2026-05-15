package com.raychang.locker.presentation.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagManagementScreen(
    onNavigateBack: () -> Unit,
    viewModel: TagManagementViewModel = hiltViewModel()
) {
    val tags by viewModel.tags.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tag_management_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Input area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.newTagName,
                    onValueChange = viewModel::onNewTagNameChange,
                    label = {
                        Text(stringResource(if (uiState.editingTag != null) R.string.rename_tag else R.string.new_tag_name))
                    },
                    isError = uiState.errorResId != null,
                    supportingText = uiState.errorResId?.let { { Text(stringResource(it)) } },
                    singleLine = true,
                    shape = RoundedCornerShape(AppStyle.CornerRadius.size12),
                )
                Spacer(modifier = Modifier.width(AppStyle.Spacing.size8))
                if (uiState.editingTag != null) {
                    FilledIconButton(
                        onClick = viewModel::saveEdit,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(Icons.Default.Check, contentDescription = stringResource(R.string.save))
                    }
                    Spacer(modifier = Modifier.width(AppStyle.Spacing.size4))
                    FilledIconButton(
                        onClick = viewModel::cancelEditing,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.cancel))
                    }
                } else {
                    FilledIconButton(
                        onClick = viewModel::createTag,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_tag))
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))

            // Tag list
            if (tags.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppStyle.Spacing.size32),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_tags_yet),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size8),
                    verticalArrangement = Arrangement.spacedBy(AppStyle.Spacing.size8)
                ) {
                    itemsIndexed(tags, key = { _, tag -> tag.id }) { index, tag ->
                        Card(
                            shape = RoundedCornerShape(AppStyle.CornerRadius.size12),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size12),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Tag icon with index badge
                                Box(
                                    modifier = Modifier
                                        .size(AppStyle.IconSize.size36)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Tag,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(AppStyle.IconSize.size18)
                                    )
                                }

                                Spacer(modifier = Modifier.width(AppStyle.Spacing.size12))

                                // Tag name + order
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = tag.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "#${index + 1}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                // Actions
                                IconButton(onClick = { viewModel.startEditing(tag) }) {
                                    Icon(
                                        Icons.Default.Edit,
                                        contentDescription = stringResource(R.string.edit),
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(AppStyle.IconSize.size20)
                                    )
                                }
                                IconButton(onClick = { viewModel.deleteTag(tag) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = stringResource(R.string.delete),
                                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                                        modifier = Modifier.size(AppStyle.IconSize.size20)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
