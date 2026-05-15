## Why

Users need a secure, offline-first way to store and manage account credentials on their Android devices. Locker is a password manager app that keeps all data locally using Room, protected by a master password (or biometric), with optional Google Drive backup/restore for data portability.

## What Changes

- Add master password setup flow for first-time users and login screen for returning users
- Implement a two-tab home layout (Home / Settings) using bottom navigation
- Build credential list on Home screen, grouped by user-defined tags as section headers
- Add credential detail screen with copy actions for username/password
- Add expandable list items with a global "expand all" toggle in the top-right corner
- Add floating action button to create new credentials
- Implement session management via DataStore: auto-logout after 5 minutes of inactivity
- Implement biometric (fingerprint) login as an alternative to password
- Build Settings screen: privacy policy, login method toggle, logout, tag management, password change
- Add Google Drive backup (upload) and restore (download & merge) functionality
- Store all credential data in a local Room database
- Use MVVM + Clean Architecture, single Activity, Compose Navigation, and Hilt DI

## Capabilities

### New Capabilities
- `master-auth`: Master password setup, login, biometric authentication, and session timeout management
- `credential-management`: CRUD operations for credentials (account, password, description) grouped by tags
- `tag-management`: Create, edit, and delete tags used to categorize credentials
- `home-screen`: Home tab with tagged credential list, expand/collapse toggle, and FAB for adding items
- `settings-screen`: Settings tab with privacy policy, auth method toggle, logout, tag management, password change
- `google-drive-backup`: Upload encrypted backup to Google Drive and download/restore from it
- `navigation-shell`: Single-activity Compose Navigation setup with bottom navigation bar (Home / Settings)

### Modified Capabilities
<!-- No existing capabilities to modify - this is a greenfield project -->

## Impact

- **Code**: Complete app rewrite from template scaffold; new packages for data, domain, and presentation layers following Clean Architecture
- **Dependencies**: Room, Hilt, DataStore, Compose Navigation, Material3, BiometricPrompt, Google Drive API, Google Sign-In
- **Manifest**: New permissions for biometric hardware and internet access; potential Google Sign-In activity registration
- **Build config**: New Gradle dependencies and Hilt/Room annotation processor configuration
