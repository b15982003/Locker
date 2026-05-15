package com.raychang.locker.presentation.navigation

object Routes {
    const val SPLASH = "splash"
    const val SETUP_PASSWORD = "setup_password"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val HOME = "home"
    const val SETTINGS = "settings"
    const val CREDENTIAL_DETAIL = "credential_detail/{id}"
    const val CREDENTIAL_EDIT = "credential_edit?id={id}"
    const val TAG_MANAGEMENT = "tag_management"
    const val CHANGE_PASSWORD = "change_password"
    const val PRIVACY_POLICY = "privacy_policy"
    const val BACKUP_RESTORE = "backup_restore"

    fun credentialDetail(id: Long) = "credential_detail/$id"
    fun credentialEdit(id: Long? = null) = if (id != null) "credential_edit?id=$id" else "credential_edit"
}
