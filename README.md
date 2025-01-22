# RandomUser Inc.

This application consists of two screens:

### User List
Displays a list of users with their **first name**, **last name**, **email**, **phone number**, and **image**. This list is initially fetched from the API at [https://api.randomuser.me/](https://api.randomuser.me/). Subsequent loads will be fetched from the phone's local database.

#### Extra Actions:
* **Filter by first name, last name, or email**: Filters the local list and displays only users whose information matches the entered text.
* **Load more users**: Requests the next 20 users from the same service mentioned earlier.
* **Delete user**: Removes the user from the database and stores their email to prevent loading them again, even if the service provides the user.

### User Detail
Displays a screen with the user's details:
* Gender
* First and Last Name
* Location: street, city, state, country
* Registration date (MM/dd/yyyy)
* Image

## Architecture
This project follows the **Clean Architecture** principles. Additionally, for presentation, the **Model-View-Intent (MVI)** pattern has been used.

**Jetpack Compose** is used throughout the app, including for navigation.

### Presentation
The app is designed as a multi-module application, where we created a module for the core of the application and a separate module for each feature. The structure is divided into three modules:
* **app**: Contains the `App` class, the entry activity, and screen navigation.
* **feature-users**: Classes required for the user list feature.
* **feature-detail**: Classes required for the user detail feature.

Each **feature-XXX** module includes a Screen, ViewModel, UIMapper, UIModel, State, UIEffect (if applicable), and the necessary components for the screen.

### Data
The Data layer is responsible for communication with the services and the database. It contains the implementation of repositories, data sources, response models, mappers, API, and DAOs.

### Domain
Contains the repository interfaces, UseCases, and necessary models.

## Testing
Unit tests have been implemented for the components prone to errors:
* ViewModels
* UseCases
* Repositories
* DataSources
* Mappers

For Unit Test development, the libraries **JUnit, MockK, and kotlinx-coroutines-test** have been used.

## Dependency Injection
Dependency injection is used to provide dependencies externally, without them being implemented within the class itself. The **Hilt** library from Dagger is used for this purpose.

## Services
The following libraries are used for service communication:
* **Gson**: For serializing responses.
* **Retrofit**: For simple communication with the RESTful API.
* **OkHttp3**: For logging service requests and responses.

`Response<T>` could have been used to handle service errors or intercept errors at some point for proper mapping and optimal error management. However, due to the limited scope of the project, which only uses one service, this has been omitted.

## Database
For persistent data storage between sessions, the **Room** library has been used.

## Images in Compose
To load images asynchronously in Compose, the **Coil** library has been used.
