## ADDED Requirements

### Requirement: Create credential
The system SHALL allow the user to create a new credential by providing a title, account (username/email), password, description, and selecting a tag. The credential SHALL be stored in the Room database.

#### Scenario: User creates a new credential with all fields
- **WHEN** the user fills in title, account, password, description, selects a tag, and submits
- **THEN** the system saves the credential to Room with the current timestamp and navigates back to the home screen

#### Scenario: User submits with missing required fields
- **WHEN** the user submits the form without filling in title, account, or password
- **THEN** the system displays validation errors on the empty required fields and does not save

### Requirement: View credential detail
The system SHALL display a detail screen for a selected credential showing title, account, password (masked by default), description, and associated tag.

#### Scenario: User taps a credential in the list
- **WHEN** the user taps a credential item in the home list
- **THEN** the system navigates to the credential detail screen showing all credential fields

#### Scenario: User toggles password visibility on detail screen
- **WHEN** the user taps the show/hide password toggle on the detail screen
- **THEN** the password field toggles between masked and plaintext display

### Requirement: Edit credential
The system SHALL allow the user to edit an existing credential's title, account, password, description, and tag.

#### Scenario: User edits and saves a credential
- **WHEN** the user modifies fields on the edit screen and submits
- **THEN** the system updates the credential in Room with a new updatedAt timestamp and navigates back

### Requirement: Delete credential
The system SHALL allow the user to delete a credential from the detail or edit screen.

#### Scenario: User deletes a credential
- **WHEN** the user taps delete and confirms the action
- **THEN** the system removes the credential from Room and navigates back to the home screen

### Requirement: Credential list grouped by tag
The system SHALL display credentials on the home screen as a list grouped by tag. Each tag name serves as a section header. Credentials under each tag are listed below the header.

#### Scenario: Home screen displays credentials grouped by tags
- **WHEN** the home screen loads with credentials that have assigned tags
- **THEN** credentials are displayed in a list with tag names as section headers, and each credential shows its title and description

#### Scenario: No credentials exist
- **WHEN** the home screen loads and no credentials exist in the database
- **THEN** the system displays an empty state message
