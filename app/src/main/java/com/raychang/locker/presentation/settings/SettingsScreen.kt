package com.raychang.locker.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Backup
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToTagManagement: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToBackupRestore: () -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    onLogout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val biometricEnabled by viewModel.biometricEnabled.collectAsStateWithLifecycle()
    val loggedOut by viewModel.loggedOut.collectAsStateWithLifecycle()
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(loggedOut) {
        if (loggedOut) onLogout()
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = stringResource(R.string.logout_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.logout_message),
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
                        onClick = { showLogoutDialog = false },
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
                            showLogoutDialog = false
                            viewModel.logout()
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.logout_confirm),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.settings_title)) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size8),
                text = stringResource(R.string.section_login_method),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            ListItem(
                headlineContent = { Text(stringResource(R.string.fingerprint_login)) },
                supportingContent = { Text(stringResource(R.string.fingerprint_login_desc)) },
                leadingContent = { Icon(Icons.Default.Fingerprint, contentDescription = null) },
                trailingContent = {
                    Switch(
                        checked = biometricEnabled,
                        onCheckedChange = viewModel::setBiometricEnabled,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                            checkedBorderColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                            uncheckedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }
            )

            HorizontalDivider()

            Text(
                modifier = Modifier.padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size8),
                text = stringResource(R.string.section_general),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            ListItem(
                modifier = Modifier.clickable { onNavigateToTagManagement() },
                headlineContent = { Text(stringResource(R.string.tag_management)) },
                supportingContent = { Text(stringResource(R.string.tag_management_desc)) },
                leadingContent = { Icon(Icons.Default.Tag, contentDescription = null) },
            )

            ListItem(
                modifier = Modifier.clickable { onNavigateToChangePassword() },
                headlineContent = { Text(stringResource(R.string.change_password)) },
                supportingContent = { Text(stringResource(R.string.change_password_desc)) },
                leadingContent = { Icon(Icons.Default.Lock, contentDescription = null) },
            )

            ListItem(
                modifier = Modifier.clickable { onNavigateToBackupRestore() },
                headlineContent = { Text(stringResource(R.string.google_drive_backup)) },
                supportingContent = { Text(stringResource(R.string.google_drive_backup_desc)) },
                leadingContent = { Icon(Icons.Default.Backup, contentDescription = null) },
            )

            HorizontalDivider()

            Text(
                modifier = Modifier.padding(horizontal = AppStyle.Spacing.size16, vertical = AppStyle.Spacing.size8),
                text = stringResource(R.string.section_about),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )

            ListItem(
                modifier = Modifier.clickable { onNavigateToPrivacyPolicy() },
                headlineContent = { Text(stringResource(R.string.privacy_policy)) },
                leadingContent = { Icon(Icons.Default.Policy, contentDescription = null) },
            )

            HorizontalDivider()

            ListItem(
                modifier = Modifier.clickable { showLogoutDialog = true },
                headlineContent = {
                    Text(stringResource(R.string.logout), color = MaterialTheme.colorScheme.error)
                },
                leadingContent = {
                    Icon(
                        Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            )
        }
    }
}
