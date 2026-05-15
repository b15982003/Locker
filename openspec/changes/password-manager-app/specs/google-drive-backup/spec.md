## ADDED Requirements

### Requirement: Google Sign-In for Drive access
The system SHALL require the user to sign in with their Google account (using Google Sign-In with `drive.file` scope) before performing any backup or restore operation.

#### Scenario: User signs in successfully
- **WHEN** the user taps "Sign in with Google" on the backup screen and completes Google Sign-In
- **THEN** the system obtains an access token and enables the backup/restore buttons

#### Scenario: User cancels Google Sign-In
- **WHEN** the user cancels the Google Sign-In flow
- **THEN** the system remains on the backup screen with backup/restore disabled

### Requirement: Upload backup to Google Drive
The system SHALL export all credentials and tags from Room as a JSON file and upload it to the user's Google Drive app folder.

#### Scenario: Successful backup upload
- **WHEN** the user taps "Backup" and is signed in
- **THEN** the system exports all tags and credentials as JSON, uploads the file to Google Drive's app-specific folder, and displays a success message

#### Scenario: Backup upload fails due to network error
- **WHEN** the backup upload fails due to a network error
- **THEN** the system displays an error message and the user can retry

### Requirement: Download and restore from Google Drive
The system SHALL download the backup JSON from Google Drive and replace all local data (tags and credentials) with the backup data.

#### Scenario: Successful restore
- **WHEN** the user taps "Restore", confirms the overwrite warning, and is signed in
- **THEN** the system downloads the backup JSON from Google Drive, clears local Room data, imports all tags and credentials from the JSON, and displays a success message

#### Scenario: User cancels restore confirmation
- **WHEN** the user taps "Restore" and dismisses the confirmation dialog
- **THEN** the system does not perform the restore and remains on the backup screen

#### Scenario: No backup found on Google Drive
- **WHEN** the user taps "Restore" but no backup file exists in the Drive app folder
- **THEN** the system displays a message indicating no backup was found
