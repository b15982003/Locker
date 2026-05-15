## Context

Locker is a greenfield Android password manager built on a fresh Compose template (single `MainActivity`, Material3 theming). All credential data lives on-device in Room; an optional Google Drive integration provides backup/restore. The user protects access with a master password and optionally biometrics. Session timeout (5 min inactivity) is tracked via DataStore.

The project targets min SDK 31 (Android 12+), compile SDK 36, and uses Kotlin 2.2 with Compose BOM 2024.09.

## Goals / Non-Goals

**Goals:**
- Deliver a fully offline-capable credential manager with local Room storage
- Provide master password + biometric authentication with automatic session timeout
- Enable tag-based organisation of credentials
- Allow backup/restore via Google Drive
- Follow MVVM + Clean Architecture with Hilt DI and Compose Navigation

**Non-Goals:**
- End-to-end encryption of the Room database (out of scope for v1; master password gates app access only)
- Cloud sync or real-time multi-device support
- Autofill framework integration
- Password generation
- Sharing credentials between users

## Decisions

### 1. Single-Activity Compose Navigation

**Choice**: One `MainActivity` hosting a `NavHost` with a `Scaffold` that contains a `BottomNavigationBar`.

**Rationale**: Compose Navigation is the modern standard; a single Activity avoids Fragment lifecycle complexity. The bottom bar switches between Home and Settings top-level destinations. Detail screens and auth screens are nested routes.

**Alternatives considered**:
- Multiple Activities: rejected — unnecessary complexity, breaks shared Compose state.
- Fragment-based Navigation: rejected — Compose Navigation is idiomatic for a pure-Compose app.

### 2. Clean Architecture Layer Split

**Choice**: Three-layer package structure under `com.raychang.locker`:

| Layer | Package | Contains |
|-------|---------|----------|
| Data | `data` | Room entities, DAOs, repository implementations, DataStore, Google Drive data source |
| Domain | `domain` | Use cases, repository interfaces, domain models |
| Presentation | `presentation` | Compose screens, ViewModels, UI state, navigation |

Plus a `di` package for Hilt modules.

**Rationale**: Clean Architecture keeps business logic testable and independent of framework. MVVM sits in the presentation layer (ViewModel exposes StateFlow consumed by Compose).

### 3. Room for Credential Storage

**Choice**: Room database with three core entities:

- `TagEntity(id: Long, name: String, order: Int)`
- `CredentialEntity(id: Long, tagId: Long, title: String, account: String, password: String, description: String, createdAt: Long, updatedAt: Long)`

**Rationale**: Room is the standard Android local DB, integrates well with Kotlin coroutines/Flow, and supports migrations.

**Alternatives considered**:
- EncryptedSharedPreferences: rejected — not suited for structured, queryable data.
- SQLCipher: considered for v2 — adds encryption at the DB level but increases APK size and complexity.

### 4. Master Password Storage

**Choice**: Hash the master password with PBKDF2 (or bcrypt via a lightweight library) and store the hash in DataStore (Preferences). On login, hash the input and compare.

**Rationale**: Never store plaintext passwords. PBKDF2 is available in the JDK and is sufficient for a local-only app.

### 5. Session Timeout via DataStore

**Choice**: Store `lastActiveTimestamp: Long` in Preferences DataStore. On app resume (`Lifecycle.ON_START`), compare current time vs stored time. If delta > 5 minutes, navigate to the login screen. Otherwise, update the timestamp.

**Rationale**: DataStore persists across process death, unlike in-memory timestamps. Checking on `ON_START` covers both cold starts and returns from background.

### 6. Biometric Authentication

**Choice**: Use AndroidX `BiometricPrompt` with `BIOMETRIC_STRONG` authenticators. Biometric is opt-in via Settings; when enabled, the login screen shows a fingerprint option alongside password entry.

**Rationale**: `BiometricPrompt` handles hardware differences across devices. `BIOMETRIC_STRONG` ensures Class 3 biometric security.

### 7. Google Drive Backup / Restore

**Choice**: Use Google Drive REST API v3 via Google Sign-In (with `drive.file` scope). Backup exports the Room DB as a JSON file to the app's Drive folder. Restore downloads and replaces local data.

**Rationale**: `drive.file` scope is narrow (app-only folder) so it avoids broad Drive permissions. JSON format allows schema validation on import.

**Alternatives considered**:
- Raw `.db` file upload: rejected — fragile across schema migrations.
- Drive Android API (deprecated): rejected — REST API is the supported path.

### 8. Navigation Graph Structure

```
NavGraph
├── auth/
│   ├── setup-password   (first-time)
│   └── login            (returning user)
├── main/                (Scaffold + BottomBar)
│   ├── home             (credential list by tag)
│   ├── settings         (settings list)
│   ├── credential-detail/{id}
│   ├── credential-edit/{id?}  (create or edit)
│   ├── tag-management
│   └── change-password
└── privacy-policy
```

## Risks / Trade-offs

- **[No DB encryption]** Room data is unencrypted on disk. A rooted device could read it. → Mitigation: acceptable for v1; plan SQLCipher for v2.
- **[Google Sign-In complexity]** OAuth setup requires a GCP project and SHA-1 fingerprint config. → Mitigation: document setup steps; backup feature is optional.
- **[Session timeout granularity]** Checking only on `ON_START` means a user who stays in the foreground indefinitely won't be timed out. → Mitigation: acceptable — the threat model is "someone picks up my phone", not "active session hijacking".
- **[Data loss on restore]** Drive restore replaces all local data. → Mitigation: warn the user with a confirmation dialog before overwriting.
