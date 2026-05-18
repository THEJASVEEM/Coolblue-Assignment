# Coolblue Assignment

Android product search app built with Kotlin and Jetpack Compose.

## Features

- Product search using the provided API
- Product grid with remote image loading
- Manual pagination
- Loading, empty, error, and pagination error states
- Retry and clear search actions
- Light/dark theme support or light design aligned with Figma
- Unit tests for mapper, repository, use case, and ViewModel

## Tech Stack

- Kotlin
- Jetpack Compose
- Hilt
- Retrofit
- Kotlinx Serialization
- Coil
- Coroutines and StateFlow
- JUnit

## Architecture

The app follows a MVVM layered architecture:

- `data`: API, DTOs, remote data source, repository implementation, mappers
- `domain`: models, repository contract, use case
- `ui`: Compose screens, components, ViewModel, UI state/events
- `core`: result handling, DI, network configuration

## Testing

The project includes unit tests for:

- DTO to domain mapping
- Repository success and error handling
- Search use case query validation and trimming
- Search ViewModel state transitions
- Pagination Error scenario UI testing
