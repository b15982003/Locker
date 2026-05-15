## ADDED Requirements

### Requirement: Single Activity with Compose NavHost
The app SHALL use a single Activity (MainActivity) that hosts a Compose NavHost as the sole navigation mechanism. All screens are Compose destinations within this NavHost.

#### Scenario: App launches into NavHost
- **WHEN** the app launches
- **THEN** MainActivity sets the Compose content with a NavHost that determines the start destination based on authentication state

### Requirement: Bottom navigation bar
The app SHALL display a bottom navigation bar with two tabs: Home and Settings. The bottom bar SHALL only be visible on the Home and Settings screens (not on auth, detail, or edit screens).

#### Scenario: User sees bottom navigation on home screen
- **WHEN** the user is on the home screen
- **THEN** a bottom navigation bar is displayed with Home (selected) and Settings tabs

#### Scenario: User switches to settings tab
- **WHEN** the user taps the Settings tab in the bottom navigation
- **THEN** the system navigates to the settings screen with the Settings tab selected

#### Scenario: Bottom bar hidden on detail screens
- **WHEN** the user navigates to a credential detail, edit, or auth screen
- **THEN** the bottom navigation bar is not visible

### Requirement: Auth-gated navigation
The NavHost start destination SHALL be determined by the authentication state: if no master password exists, navigate to setup; if the session is expired, navigate to login; otherwise navigate to the main (home) screen.

#### Scenario: No master password set
- **WHEN** the app launches and no master password hash exists in DataStore
- **THEN** the NavHost start destination is the password setup screen

#### Scenario: Session expired
- **WHEN** the app launches and a master password exists but last active time exceeds 5 minutes
- **THEN** the NavHost start destination is the login screen

#### Scenario: Session still valid
- **WHEN** the app launches and the session is still valid (within 5 minutes)
- **THEN** the NavHost start destination is the home screen and the last active timestamp is refreshed

### Requirement: Back navigation
The system SHALL support standard Android back navigation. From detail/edit screens, back navigates to the previous screen. From auth screens, back exits the app.

#### Scenario: Back from detail screen
- **WHEN** the user presses back on the credential detail screen
- **THEN** the system navigates back to the home screen

#### Scenario: Back from login screen
- **WHEN** the user presses back on the login screen
- **THEN** the app exits (back stack is empty)
