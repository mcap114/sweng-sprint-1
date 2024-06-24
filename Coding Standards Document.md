# CODING STANDARDS DOCUMENTATION | HOUSEHOLD PROFILING DATABASE
Software coding standards to be used for this project are described in this document.

This document's format is based on [Axolo.co's Coding Style Template](https://axolo.co/blog/p/coding-style-template).

## I. Project Structure
### Background
This project is built with a Java-based backend for business logic with web-based frontend, integrated together with Spring Boot.
### Directory Layout and File Organization
This project follows the MVC design pattern with the following folder structure for the main directory:
- **Model**
  - Contains classes that directly manage database information.
- **Controller**
  - Contains classes that take input and convert to commands for **Model** or **View**.
- **View**
  - Contains classes that deal with presentation and user view.
- **Util**
  - Contains utility classes as well as custom exception classes.
- **Resources**
  - Contains any non-code resources used (i.e. images, fonts).

Another folder called `resources` outside the main directory is where the frontend elements are stored:
- **static**
  - Contains `html`, `css`, and `javascript`.


- **templates**
  - Contains template files (`hbs`, etc.).



Class names use Pascal Case (e.g. `DatabaseManager.java`), methods use Camel Case (e.g. `insertMember()`).

## II. Error Handling
Use `try-catch` blocks. Use `throw` keyword for any custom errors.

When creating custom error classes, extend the `Exception` class and add the specific error tackled. (e.g. `ValidationException`)


## III. Version Control
This project uses git version control and Semantic Versioning.

### Commit Message Format
Include keywords like `fix`, `add`, `remove`, etc. at the beginning of commit messages.


## IV. Testing
Follow provided `CSSWENG` test script format.

## V. Documentation
This project uses the following documentation:
- Coding Standards Doc
- Javadoc
- Changelog Document
- Github Commit log

### Writing and Updating
Be clear and informative as possible, use a friendly, neutral tone.
Update said documents whenever most appropriate (i.e. major changes are listed in the `CHANGELOG`, new additions to the tech stack are added to the `CSD`, etc.).

