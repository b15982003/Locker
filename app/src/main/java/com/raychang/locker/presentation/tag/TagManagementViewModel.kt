package com.raychang.locker.presentation.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raychang.locker.R
import com.raychang.locker.domain.model.Tag
import com.raychang.locker.domain.usecase.tag.CreateTagUseCase
import com.raychang.locker.domain.usecase.tag.DeleteTagUseCase
import com.raychang.locker.domain.usecase.tag.GetTagsUseCase
import com.raychang.locker.domain.usecase.tag.ReorderTagsUseCase
import com.raychang.locker.domain.usecase.tag.UpdateTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagManagementViewModel @Inject constructor(
    getTagsUseCase: GetTagsUseCase,
    private val createTagUseCase: CreateTagUseCase,
    private val updateTagUseCase: UpdateTagUseCase,
    private val deleteTagUseCase: DeleteTagUseCase,
    private val reorderTagsUseCase: ReorderTagsUseCase
) : ViewModel() {

    val tags: StateFlow<List<Tag>> = getTagsUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _uiState = MutableStateFlow(TagManagementUiState())
    val uiState: StateFlow<TagManagementUiState> = _uiState.asStateFlow()

    fun onNewTagNameChange(name: String) {
        _uiState.update { it.copy(newTagName = name, errorResId = null) }
    }

    fun createTag() {
        val name = _uiState.value.newTagName.trim()
        if (name.isBlank()) {
            _uiState.update { it.copy(errorResId = R.string.error_tag_empty) }
            return
        }
        if (tags.value.any { it.name.equals(name, ignoreCase = true) }) {
            _uiState.update { it.copy(errorResId = R.string.error_tag_exists) }
            return
        }
        viewModelScope.launch {
            createTagUseCase(name)
            _uiState.update { it.copy(newTagName = "", errorResId = null) }
        }
    }

    fun startEditing(tag: Tag) {
        _uiState.update { it.copy(editingTag = tag, newTagName = tag.name, errorResId = null) }
    }

    fun cancelEditing() {
        _uiState.update { it.copy(editingTag = null, newTagName = "", errorResId = null) }
    }

    fun saveEdit() {
        val tag = _uiState.value.editingTag ?: return
        val name = _uiState.value.newTagName.trim()
        if (name.isBlank()) {
            _uiState.update { it.copy(errorResId = R.string.error_tag_empty) }
            return
        }
        if (tags.value.any { it.id != tag.id && it.name.equals(name, ignoreCase = true) }) {
            _uiState.update { it.copy(errorResId = R.string.error_tag_exists) }
            return
        }
        viewModelScope.launch {
            updateTagUseCase(tag.copy(name = name))
            _uiState.update { it.copy(editingTag = null, newTagName = "", errorResId = null) }
        }
    }

    fun deleteTag(tag: Tag) {
        viewModelScope.launch {
            deleteTagUseCase(tag)
        }
    }

    fun reorderTags(from: Int, to: Int) {
        val currentList = tags.value.toMutableList()
        val item = currentList.removeAt(from)
        currentList.add(to, item)
        viewModelScope.launch {
            reorderTagsUseCase(currentList)
        }
    }
}
