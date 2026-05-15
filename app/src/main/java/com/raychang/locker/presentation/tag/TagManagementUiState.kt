package com.raychang.locker.presentation.tag

import androidx.annotation.StringRes
import com.raychang.locker.domain.model.Tag

data class TagManagementUiState(
    val editingTag: Tag? = null,
    val newTagName: String = "",
    @StringRes val errorResId: Int? = null
)
