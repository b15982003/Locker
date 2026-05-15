package com.raychang.locker.presentation.password

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.raychang.locker.R
import com.raychang.locker.ui.theme.AppStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onNavigateBack: () -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val successMsg = stringResource(R.string.password_changed_success)

    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            Toast.makeText(context, successMsg, Toast.LENGTH_SHORT).show()
            onNavigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.change_password)) },
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
        ) {
            val fieldShape = RoundedCornerShape(AppStyle.CornerRadius.size12)

            OutlinedTextField(
                value = uiState.currentPassword,
                onValueChange = viewModel::onCurrentPasswordChange,
                label = { Text(stringResource(R.string.current_password)) },
                singleLine = true,
                shape = fieldShape,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedTextField(
                value = uiState.newPassword,
                onValueChange = viewModel::onNewPasswordChange,
                label = { Text(stringResource(R.string.new_password)) },
                singleLine = true,
                shape = fieldShape,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size12))

            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text(stringResource(R.string.confirm_new_password)) },
                singleLine = true,
                shape = fieldShape,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (uiState.errorResId != null) {
                Spacer(modifier = Modifier.height(AppStyle.Spacing.size8))
                Text(
                    text = stringResource(uiState.errorResId!!),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(AppStyle.Spacing.size24))

            Button(
                onClick = viewModel::onSubmit,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(AppStyle.CornerRadius.size12)
            ) {
                Text(stringResource(R.string.change_password_button))
            }
        }
    }
}
