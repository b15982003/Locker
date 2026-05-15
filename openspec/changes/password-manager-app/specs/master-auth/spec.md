## ADDED Requirements

### Requirement: First-time password setup
The system SHALL present a password setup screen when no master password hash exists in DataStore. The user MUST enter and confirm a password. The system SHALL store the PBKDF2 hash of the password in DataStore and navigate to the home screen upon success.

#### Scenario: First launch with no stored password
- **WHEN** the app launches and no master password hash is found in DataStore
- **THEN** the system displays the password setup screen with "enter password" and "confirm password" fields

#### Scenario: Passwords match during setup
- **WHEN** the user enters matching passwords and submits
- **THEN** the system hashes the password with PBKDF2, stores the hash in DataStore, records the current timestamp as last active time, and navigates to the home screen

#### Scenario: Passwords do not match during setup
- **WHEN** the user enters mismatched passwords and submits
- **THEN** the system displays an error message and does not proceed

### Requirement: Password login
The system SHALL present a login screen when a master password hash exists in DataStore and the session has expired. The user MUST enter the correct master password to access the app.

#### Scenario: Correct password entered
- **WHEN** the user enters the correct password on the login screen
- **THEN** the system updates the last active timestamp in DataStore and navigates to the home screen

#### Scenario: Incorrect password entered
- **WHEN** the user enters an incorrect password on the login screen
- **THEN** the system displays an error message and remains on the login screen

### Requirement: Biometric login
The system SHALL support fingerprint authentication as an alternative login method when enabled in settings. Biometric login uses AndroidX BiometricPrompt with BIOMETRIC_STRONG authenticators.

#### Scenario: Biometric enabled and user authenticates via fingerprint
- **WHEN** biometric login is enabled and the user successfully authenticates via fingerprint on the login screen
- **THEN** the system updates the last active timestamp and navigates to the home screen

#### Scenario: Biometric authentication fails
- **WHEN** the user fails biometric authentication
- **THEN** the system remains on the login screen and the user can retry or fall back to password login

#### Scenario: Biometric not available on device
- **WHEN** the device does not support BIOMETRIC_STRONG authenticators
- **THEN** the biometric login option SHALL NOT be shown on the login screen and the settings toggle SHALL be disabled

### Requirement: Session timeout
The system SHALL track the last active timestamp in DataStore. On app resume (Lifecycle ON_START), if the elapsed time since the last active timestamp exceeds 5 minutes, the system SHALL navigate to the login screen. Otherwise, the system SHALL update the timestamp to the current time.

#### Scenario: App resumed within 5 minutes
- **WHEN** the app resumes and the elapsed time since last active is less than or equal to 5 minutes
- **THEN** the system updates the last active timestamp to the current time and the user remains on their current screen

#### Scenario: App resumed after more than 5 minutes
- **WHEN** the app resumes and the elapsed time since last active exceeds 5 minutes
- **THEN** the system navigates to the login screen and clears the back stack

### Requirement: Change master password
The system SHALL allow the user to change their master password from the settings screen. The user MUST enter the current password and a new password (with confirmation).

#### Scenario: Successful password change
- **WHEN** the user enters the correct current password and matching new passwords
- **THEN** the system replaces the stored hash with the new password's hash and shows a success message

#### Scenario: Current password incorrect during change
- **WHEN** the user enters an incorrect current password
- **THEN** the system displays an error and does not change the password
