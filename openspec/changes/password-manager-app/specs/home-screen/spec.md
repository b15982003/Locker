## ADDED Requirements

### Requirement: Credential list display
The home screen SHALL display a scrollable list of credentials grouped by tag. Each tag name is rendered as a section header. Each credential item shows its title and description text by default.

#### Scenario: Credentials are displayed grouped by tag
- **WHEN** the home screen loads with tagged credentials
- **THEN** the list shows tag names as sticky section headers with credential items underneath, each showing title and description

#### Scenario: Untagged credentials
- **WHEN** credentials exist without a tag assignment
- **THEN** they are displayed under an "Untagged" section at the bottom of the list

### Requirement: Expandable credential items
Each credential item in the list SHALL support an expanded state that shows the account and masked password in addition to the title and description. Items are collapsed by default, showing only the title and description.

#### Scenario: User taps a credential item to expand
- **WHEN** the user taps a credential item in the list
- **THEN** the item expands to show account and masked password fields, and tapping again collapses it

#### Scenario: User taps expanded item to navigate to detail
- **WHEN** a credential item is in expanded state and the user taps a "detail" action
- **THEN** the system navigates to the credential detail screen

### Requirement: Global expand all toggle
The home screen SHALL have a toggle button in the top-right corner of the app bar that expands or collapses all credential items simultaneously.

#### Scenario: User enables expand all
- **WHEN** the user taps the expand-all toggle in the top-right corner
- **THEN** all credential items in the list expand to show their full content

#### Scenario: User disables expand all
- **WHEN** the user taps the expand-all toggle again
- **THEN** all credential items collapse back to showing only title and description

### Requirement: Add new credential FAB
The home screen SHALL display a floating action button (FAB) at the bottom of the screen for adding new credentials.

#### Scenario: User taps FAB to add credential
- **WHEN** the user taps the FAB on the home screen
- **THEN** the system navigates to the credential creation screen

### Requirement: Empty state
The home screen SHALL display an empty state when no credentials exist.

#### Scenario: No credentials in database
- **WHEN** the home screen loads and the database contains no credentials
- **THEN** the system displays an empty state message encouraging the user to add their first credential
