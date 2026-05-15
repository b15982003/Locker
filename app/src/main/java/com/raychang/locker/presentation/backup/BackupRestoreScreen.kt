package com.raychang.locker.presentation.backup

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupRestoreScreen(
    onNavigateBack: () -> Unit,
    viewModel: BackupRestoreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showRestoreConfirmation by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // File picker for import
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { viewModel.importFromFile(it) }
    }

    // Launch email intent when ready
    LaunchedEffect(uiState.emailIntent) {
        uiState.emailIntent?.let { intent ->
            context.startActivity(intent)
            viewModel.onEmailIntentHandled()
        }
    }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    if (showRestoreConfirmation) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirmation = false },
            title = {
                Text(
                    text = stringResource(R.string.restore_data_title),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.restore_warning),
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
                        onClick = { showRestoreConfirmation = false },
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
                            showRestoreConfirmation = false
                            filePickerLauncher.launch(arrayOf("application/json", "*/*"))
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.restore),
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
                title = { Text(stringResource(R.string.backup_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(AppStyle.Spacing.size16)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(AppStyle.Spacing.size16))
            }

            // Export section
            Text(
                text = stringResource(R.string.export_via_email),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text(stringResource(R.string.backup_email_hint)) },
                singleLine = true,
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12),
                leadingIcon = {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            Button(
                onClick = viewModel::exportAndSendEmail,
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12)
            ) {
                Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(AppStyle.IconSize.size18))
                Spacer(modifier = Modifier.padding(horizontal = AppStyle.Spacing.size4))
                Text(stringResource(R.string.export_via_email))
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size24))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size24))

            // Import section
            Text(
                text = stringResource(R.string.import_from_file),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            // Restore guide card
            Card(
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(AppStyle.Spacing.size16)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(AppStyle.IconSize.size18)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = AppStyle.Spacing.size4))
                        Text(
                            text = stringResource(R.string.restore_guide_title),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size10))
                    Text(
                        text = stringResource(R.string.restore_guide_step1),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))
                    Text(
                        text = stringResource(R.string.restore_guide_step2),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))
                    Text(
                        text = stringResource(R.string.restore_guide_step3),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(AppStyle.Spacing.size4))
                    Text(
                        text = stringResource(R.string.restore_guide_step4),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedButton(
                onClick = { showRestoreConfirmation = true },
                enabled = !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12)
            ) {
                Icon(Icons.Default.FileOpen, contentDescription = null, modifier = Modifier.size(AppStyle.IconSize.size18))
                Spacer(modifier = Modifier.padding(horizontal = AppStyle.Spacing.size4))
                Text(stringResource(R.string.import_from_file))
            }
        }
    }
}
