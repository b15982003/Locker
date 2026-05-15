package com.raychang.locker.presentation.credential.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialEditScreen(
    credentialId: Long?,
    onNavigateBack: () -> Unit,
    viewModel: CredentialEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tags by viewModel.tags.collectAsStateWithLifecycle()
    var passwordVisible by remember { mutableStateOf(false) }
    var tagDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(credentialId) {
        if (credentialId != null) viewModel.loadCredential(credentialId)
    }

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onNavigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(if (uiState.isEditMode) R.string.edit_credential_title else R.string.new_credential_title)) },
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
                .padding(AppStyle.Spacing.size16)
                .verticalScroll(rememberScrollState())
        ) {
            val fieldShape = RoundedCornerShape(AppStyle.CornerRadius.size12)

            OutlinedTextField(
                value = uiState.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text(stringResource(R.string.title_required)) },
                isError = uiState.errors.containsKey("title"),
                supportingText = uiState.errors["title"]?.let { { Text(stringResource(it)) } },
                singleLine = true,
                shape = fieldShape,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedTextField(
                value = uiState.account,
                onValueChange = viewModel::onAccountChange,
                label = { Text(stringResource(R.string.account_required)) },
                isError = uiState.errors.containsKey("account"),
                supportingText = uiState.errors["account"]?.let { { Text(stringResource(it)) } },
                singleLine = true,
                shape = fieldShape,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(stringResource(R.string.password_required)) },
                isError = uiState.errors.containsKey("password"),
                supportingText = uiState.errors["password"]?.let { { Text(stringResource(it)) } },
                singleLine = true,
                shape = fieldShape,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = stringResource(if (passwordVisible) R.string.hide else R.string.show)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text(stringResource(R.string.description_hint)) },
                minLines = 2,
                maxLines = 4,
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12),
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            val noTagText = stringResource(R.string.no_tag)
            ExposedDropdownMenuBox(
                expanded = tagDropdownExpanded,
                onExpandedChange = { tagDropdownExpanded = it }
            ) {
                val selectedTag = tags.find { it.id == uiState.selectedTagId }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    value = selectedTag?.name ?: noTagText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.tag_hint)) },
                    shape = fieldShape,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tagDropdownExpanded) },
                )
                ExposedDropdownMenu(
                    expanded = tagDropdownExpanded,
                    onDismissRequest = { tagDropdownExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(noTagText) },
                        onClick = {
                            viewModel.onTagSelected(null)
                            tagDropdownExpanded = false
                        }
                    )
                    tags.forEach { tag ->
                        DropdownMenuItem(
                            text = { Text(tag.name) },
                            onClick = {
                                viewModel.onTagSelected(tag.id)
                                tagDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size24))

            Button(
                onClick = viewModel::onSave,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12)
            ) {
                Text(stringResource(if (uiState.isEditMode) R.string.save_changes else R.string.create))
            }
        }
    }
}
