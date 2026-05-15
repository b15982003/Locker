package com.raychang.locker.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.UnfoldLess
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val taggedCredentials by viewModel.taggedCredentials.collectAsStateWithLifecycle()
    val expandAll by viewModel.expandAll.collectAsStateWithLifecycle()
    val showAllPasswords by viewModel.showAllPasswords.collectAsStateWithLifecycle()
    val expandedItems = remember { mutableStateMapOf<Long, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) },
                actions = {
                    if (expandAll) {
                        IconButton(onClick = viewModel::toggleShowAllPasswords) {
                            Icon(
                                imageVector = if (showAllPasswords) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = stringResource(if (showAllPasswords) R.string.hide_password else R.string.show_password)
                            )
                        }
                    }
                    IconButton(onClick = {
                        expandedItems.clear()
                        viewModel.toggleExpandAll()
                    }) {
                        Icon(
                            imageVector = if (expandAll) Icons.Default.UnfoldLess else Icons.Default.UnfoldMore,
                            contentDescription = stringResource(if (expandAll) R.string.collapse_all else R.string.expand_all)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            OutlinedButton(
                onClick = onNavigateToCreate,
                modifier = Modifier
                    .padding(bottom = AppStyle.Spacing.size72)
                    .height(AppStyle.Height.size48),
                shape = CutCornerShape(topStart = AppStyle.CornerRadius.size14, bottomEnd = AppStyle.CornerRadius.size14),
                border = BorderStroke(AppStyle.BorderWidth.size1, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_credential),
                    modifier = Modifier.size(AppStyle.IconSize.size18)
                )
                Spacer(modifier = Modifier.width(AppStyle.Spacing.size6))
                Text(
                    text = "[ + NEW ]",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    ) { innerPadding ->
        if (taggedCredentials.isEmpty() || taggedCredentials.all { it.credentials.isEmpty() }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty_state_message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = AppStyle.Spacing.size100)
            ) {
                taggedCredentials.forEach { group ->
                    item(key = "header_${group.tag?.id ?: "untagged"}") {
                        TagSectionHeader(tagName = group.tag?.name ?: stringResource(R.string.untagged))
                    }
                    items(
                        items = group.credentials,
                        key = { it.id }
                    ) { credential ->
                        val isExpanded = expandedItems[credential.id] ?: expandAll
                        CredentialListItem(
                            credential = credential,
                            isExpanded = isExpanded,
                            showPassword = showAllPasswords,
                            onToggleExpand = {
                                expandedItems[credential.id] = !isExpanded
                            },
                            onNavigateToDetail = { onNavigateToDetail(credential.id) },
                            modifier = Modifier.padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size4)
                        )
                    }
                }
            }
        }
    }
}
