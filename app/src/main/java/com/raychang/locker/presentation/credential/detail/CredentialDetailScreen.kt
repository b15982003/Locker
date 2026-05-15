package com.raychang.locker.presentation.credential.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialDetailScreen(
    credentialId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (Long) -> Unit,
    viewModel: CredentialDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(credentialId) {
        viewModel.loadCredential(credentialId)
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) onNavigateBack()
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.delete_credential_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.delete_credential_message),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppStyle.Spacing.size12)
                ) {
                    Button(
                        onClick = { showDeleteDialog = false },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.cancel),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Button(
                        onClick = {
                            showDeleteDialog = false
                            viewModel.deleteCredential()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.delete),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToEdit(credentialId) }) {
                        Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit))
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                    }
                }
            )
        }
    ) { innerPadding ->
        val credential = uiState.credential
        if (credential != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(AppStyle.Spacing.size16)
            ) {
                DetailField(stringResource(R.string.title_label), credential.title)
                Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))
                if (uiState.tagName != null) {
                    DetailField(stringResource(R.string.tag_label), uiState.tagName!!)
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))
                }
                DetailField(stringResource(R.string.account_label), credential.account)
                Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(AppStyle.Spacing.size16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.password_label),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))
                            Text(
                                text = if (uiState.passwordVisible) credential.password else "\u2022".repeat(credential.password.length.coerceAtMost(16)),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        IconButton(onClick = viewModel::togglePasswordVisibility) {
                            Icon(
                                imageVector = if (uiState.passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = stringResource(if (uiState.passwordVisible) R.string.hide else R.string.show)
                            )
                        }
                    }
                }
                if (credential.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))
                    DetailField(stringResource(R.string.description_label), credential.description)
                }
            }
        }
    }
}

@Composable
private fun DetailField(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(AppStyle.Spacing.size16)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
