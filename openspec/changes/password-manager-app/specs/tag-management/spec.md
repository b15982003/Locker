## ADDED Requirements

### Requirement: Create tag
The system SHALL allow the user to create a new tag by providing a name from the tag management screen in settings.

#### Scenario: User creates a new tag
- **WHEN** the user enters a tag name and submits on the tag management screen
- **THEN** the system saves the tag to Room and it appears in the tag list

#### Scenario: User creates a tag with a duplicate name
- **WHEN** the user enters a tag name that already exists
- **THEN** the system displays an error indicating the tag name is already in use

### Requirement: Edit tag
The system SHALL allow the user to rename an existing tag.

#### Scenario: User renames a tag
- **WHEN** the user selects a tag, enters a new name, and submits
- **THEN** the system updates the tag name in Room and the change is reflected in the credential list headers

### Requirement: Delete tag
The system SHALL allow the user to delete a tag. Credentials associated with the deleted tag SHALL have their tag reference cleared (set to null/untagged).

#### Scenario: User deletes a tag with associated credentials
- **WHEN** the user deletes a tag that has credentials assigned to it
- **THEN** the system removes the tag from Room and sets the tagId of associated credentials to null

#### Scenario: User deletes a tag with no associated credentials
- **WHEN** the user deletes a tag that has no credentials
- **THEN** the system removes the tag from Room

### Requirement: Tag ordering
The system SHALL allow the user to reorder tags. The order determines the display order of tag sections on the home screen.

#### Scenario: User reorders tags
- **WHEN** the user drags a tag to a new position in the tag management list
- **THEN** the system updates the tag order values in Room and the home screen reflects the new order
