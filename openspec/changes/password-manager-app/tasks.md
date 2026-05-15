## 1. Project Setup & Dependencies

- [x] 1.1 Add Gradle dependencies: Room, Hilt, DataStore Preferences, Compose Navigation, BiometricPrompt, Google Drive REST API, Google Sign-In
- [x] 1.2 Configure Hilt with @HiltAndroidApp Application class and apply KSP/kapt plugins for Room and Hilt annotation processors
- [x] 1.3 Add internet and biometric permissions to AndroidManifest.xml

## 2. Data Layer — Room Database

- [x] 2.1 Create TagEntity (id, name, order) and CredentialEntity (id, tagId, title, account, password, description, createdAt, updatedAt) Room entities
- [x] 2.2 Create TagDao with CRUD operations and ordering queries
- [x] 2.3 Create CredentialDao with CRUD operations and query to get credentials grouped by tagId
- [x] 2.4 Create LockerDatabase RoomDatabase class with entities and DAOs
- [x] 2.5 Create Hilt DatabaseModule providing LockerDatabase, TagDao, and CredentialDao

## 3. Data Layer — DataStore & Session

- [x] 3.1 Create PreferencesManager class wrapping DataStore for master password hash, last active timestamp, and biometric-enabled preference
- [x] 3.2 Provide PreferencesManager via Hilt module

## 4. Domain Layer

- [x] 4.1 Create domain models: Tag, Credential
- [x] 4.2 Create TagRepository interface and RoomTagRepository implementation
- [x] 4.3 Create CredentialRepository interface and RoomCredentialRepository implementation
- [x] 4.4 Create AuthRepository interface and AuthRepositoryImpl (password hashing with PBKDF2, session validation, biometric preference)
- [x] 4.5 Create use cases: SetupPasswordUseCase, LoginUseCase, ValidateSessionUseCase, ChangePasswordUseCase
- [x] 4.6 Create use cases: GetCredentialsByTagUseCase, CreateCredentialUseCase, UpdateCredentialUseCase, DeleteCredentialUseCase
- [x] 4.7 Create use cases: GetTagsUseCase, CreateTagUseCase, UpdateTagUseCase, DeleteTagUseCase, ReorderTagsUseCase
- [x] 4.8 Provide repository bindings in Hilt RepositoryModule

## 5. Navigation Shell

- [x] 5.1 Define navigation routes as sealed class/object (Auth.Setup, Auth.Login, Main.Home, Main.Settings, CredentialDetail, CredentialEdit, TagManagement, ChangePassword, PrivacyPolicy, BackupRestore)
- [x] 5.2 Create AppNavHost composable with NavHost and all route destinations
- [x] 5.3 Create MainScaffold composable with Scaffold and BottomNavigationBar (Home/Settings tabs)
- [x] 5.4 Update MainActivity to set LockerTheme with AppNavHost, determine start destination based on auth state

## 6. Auth Screens

- [x] 6.1 Create SetupPasswordViewModel with password/confirm fields validation and SetupPasswordUseCase
- [x] 6.2 Create SetupPasswordScreen composable with two password fields and submit button
- [x] 6.3 Create LoginViewModel with password field, LoginUseCase, and BiometricPrompt trigger
- [x] 6.4 Create LoginScreen composable with password field, login button, and optional biometric button

## 7. Home Screen

- [x] 7.1 Create HomeViewModel exposing credential list grouped by tag as StateFlow, plus expand-all toggle state
- [x] 7.2 Create CredentialListItem composable with collapsed (title + description) and expanded (+ account + masked password) states
- [x] 7.3 Create TagSectionHeader composable for tag group headers
- [x] 7.4 Create HomeScreen composable with LazyColumn, tag sections, expand-all toggle in TopAppBar, and FAB
- [x] 7.5 Handle empty state display when no credentials exist

## 8. Credential Detail & Edit Screens

- [x] 8.1 Create CredentialDetailViewModel loading a credential by ID
- [x] 8.2 Create CredentialDetailScreen composable showing all fields with password show/hide toggle and delete action
- [x] 8.3 Create CredentialEditViewModel handling create (no ID) and edit (with ID) modes
- [x] 8.4 Create CredentialEditScreen composable with form fields, tag dropdown, and save button

## 9. Settings Screen

- [x] 9.1 Create SettingsViewModel exposing biometric toggle state and logout action
- [x] 9.2 Create SettingsScreen composable with menu items: login method settings, logout, tag management, change password, Google Drive backup, privacy policy
- [x] 9.3 Implement logout action: clear last active timestamp and navigate to login screen

## 10. Tag Management Screen

- [x] 10.1 Create TagManagementViewModel with tag list, add/edit/delete/reorder operations
- [x] 10.2 Create TagManagementScreen composable with reorderable tag list, add button, edit/delete actions per item

## 11. Change Password Screen

- [x] 11.1 Create ChangePasswordViewModel with current password verification and new password validation
- [x] 11.2 Create ChangePasswordScreen composable with current password, new password, confirm password fields

## 12. Google Drive Backup & Restore

- [x] 12.1 Create GoogleDriveDataSource class handling Google Sign-In, JSON export/import, Drive REST API upload/download
- [x] 12.2 Create BackupRestoreRepository interface and implementation orchestrating Room export to JSON and JSON import to Room
- [x] 12.3 Create BackupRestoreViewModel with sign-in state, backup action, and restore action with confirmation
- [x] 12.4 Create BackupRestoreScreen composable with Google Sign-In button, backup button, restore button with confirmation dialog

## 13. Privacy Policy Screen

- [x] 13.1 Create PrivacyPolicyScreen composable displaying static privacy policy text

## 14. Session Lifecycle

- [x] 14.1 Add Lifecycle observer in MainActivity (ON_START) that checks session timeout via ValidateSessionUseCase and navigates to login if expired, otherwise refreshes timestamp
