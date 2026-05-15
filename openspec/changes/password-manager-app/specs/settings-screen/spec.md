## ADDED Requirements

### Requirement: Settings menu items
The settings screen SHALL display the following menu items in order: Login Method Settings, Logout, Tag Management, Change Password, Google Drive Backup, Privacy Policy.

#### Scenario: Settings screen displays all options
- **WHEN** the user navigates to the settings tab
- **THEN** the screen displays all listed menu items in order

### Requirement: Login method settings
The settings screen SHALL allow the user to toggle between password-only and biometric+password login methods.

#### Scenario: User enables biometric login
- **WHEN** the user toggles biometric login on in settings and the device supports BIOMETRIC_STRONG
- **THEN** the system stores the preference and the login screen will offer biometric authentication

#### Scenario: User disables biometric login
- **WHEN** the user toggles biometric login off
- **THEN** the system stores the preference and the login screen will only show password entry

### Requirement: Logout
The settings screen SHALL provide a logout option that clears the session and returns to the login screen.

#### Scenario: User taps logout
- **WHEN** the user taps the logout option
- **THEN** the system clears the last active timestamp, navigates to the login screen, and clears the back stack

### Requirement: Navigate to tag management
The settings screen SHALL provide navigation to the tag management screen.

#### Scenario: User taps tag management
- **WHEN** the user taps the tag management option in settings
- **THEN** the system navigates to the tag management screen

### Requirement: Navigate to change password
The settings screen SHALL provide navigation to the change password screen.

#### Scenario: User taps change password
- **WHEN** the user taps the change password option in settings
- **THEN** the system navigates to the change password screen

### Requirement: Navigate to Google Drive backup
The settings screen SHALL provide navigation to the Google Drive backup screen.

#### Scenario: User taps Google Drive backup
- **WHEN** the user taps the Google Drive backup option in settings
- **THEN** the system navigates to the backup/restore screen

### Requirement: Privacy policy display
The settings screen SHALL provide access to a privacy policy screen.

#### Scenario: User taps privacy policy
- **WHEN** the user taps the privacy policy option
- **THEN** the system navigates to a screen displaying the privacy policy text
