# Change Log | Sprint 2
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).
___

## [0.2.1] - 2024-06-24

Integration of backend and frontend features.

### [ *Converted project to Spring Boot framework* ]

### Added
- **Frontend**
  - Frontend pages have been added
  - Index and Add Household functionality now working.
  - Header/Navigation dropdown draft working

- **Spring Boot**
  - Added controller classes and App classes for Spring Boot functionality.
  
### Changed
- **Project Structure**
  - View files are now in `resources` folder under `src/main`.
  - New dependencies added to `pom.xml`.


- **Model**
  - Added new column to `Members` table for storing image path.


### Known Issues
-  Image upload functionality 

___

## [0.2.0] - 2024-06-20

Batch of updates too large to summarize in commit message.

### Added
- **Documentation**
  - Javadoc and changelog added.
  

- **All Classes**
  - Added javadoc comments. 


- **Controller**
  - Created `SearchController` for search functionality.


- **Utility**
  - Added `TerminalMenus` class for displaying submenus for app operation through AppTerminal.
  - Added custom exception class `ValidationException` for user input error handling.

### Changed
- **Model**
  - Added input validation and error handling to `DatabaseManager`.


- **Controller**
  - Added input validation and error handling to `AppController`.


- **Utility**
  - New input validation and exception handling methods for ints, doubles, strings, and dates in `InputValidation`.

### Fixed
- Fixed errors regarding contact number handling in Household record creation.
- Now correctly detects missing mandatory fields.


### Known Issues
- Database connections sometimes closes during normal operation requiring app restart.

___


