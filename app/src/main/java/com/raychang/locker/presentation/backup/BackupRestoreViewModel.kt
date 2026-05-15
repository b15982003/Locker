package com.raychang.locker.presentation.backup

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.R
import com.raychang.locker.domain.repository.BackupRestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BackupRestoreViewModel @Inject constructor(
    private val backupRestoreRepository: BackupRestoreRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(BackupRestoreUiState())
    val uiState: StateFlow<BackupRestoreUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun exportAndSendEmail() {
        val email = _uiState.value.email.trim()
        if (email.isBlank()) {
            _uiState.update { it.copy(message = context.getString(R.string.error_email_empty)) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }
            try {
                val json = backupRestoreRepository.exportToJson()

                // Write to cache file
                val file = File(context.cacheDir, "locker_backup.json")
                file.writeText(json)

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/json"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.backup_email_subject))
                    putExtra(Intent.EXTRA_TEXT, context.getString(R.string.backup_email_body))
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        emailIntent = Intent.createChooser(intent, null),
                        message = context.getString(R.string.export_success)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = context.getString(R.string.export_failed, e.message ?: "")
                    )
                }
            }
        }
    }

    fun onEmailIntentHandled() {
        _uiState.update { it.copy(emailIntent = null) }
    }

    fun importFromFile(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, message = null) }
            try {
                val json = context.contentResolver.openInputStream(uri)
                    ?.bufferedReader()
                    ?.use { it.readText() }
                    ?: throw Exception("Cannot read file")

                backupRestoreRepository.importFromJson(json)
                _uiState.update {
                    it.copy(isLoading = false, message = context.getString(R.string.restore_success))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = context.getString(R.string.restore_failed, e.message ?: "")
                    )
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}
