# OTT Video Player Application

A native Android application built as part of the OTT Developer Technical Assignment. The app demonstrates clean architecture, MVVM, Jetpack components, and Media3 ExoPlayer for video streaming.

---

## APK Build

You can download the test build directly from the repository:
- **[Download app-debug.apk](apk/app-debug.apk)**

---

## Features

- **Home Screen**: Features a Hero Banner at the top and content rails ("Trending Now", "Recommended for You") using horizontal `RecyclerView`s. Data is loaded dynamically from mock JSON asset files via the data layer.
- **Detail Screen**: Shows item image, title, description, and a watch CTA that launches the player.
- **Video Player**: Uses Media3 ExoPlayer for video playback. Supports playback controls, progress tracking, and handles lifecycle events properly (`onPause`, `onResume`, `onStop`).
- **Player Events**: Captures 5 player callbacks (`PLAYER_READY`, `PLAYER_STARTED`, `PLAYER_PAUSED`, `PLAYER_SEEKED`, `PLAYER_ERROR`) using a dedicated `PlayerEventHandler` and logs them cleanly via ViewModel and Analytics Repository.

---

## Architecture & Tech Stack

The project follows Clean Architecture with MVVM:

- **Presentation Layer**: ViewModels, Fragments (`HomeFragment`, `ItemDetailsFrag`, `VideoPlayerFrag`), `StateFlow` for UI state, and ViewBinding.
- **Domain Layer**: Pure Kotlin models (`DomainItem`, `DomainCategory`, `PlayerEvent`), repository contracts, and Use Cases (`GetHomeContentUseCase`, `LogPlayerEventUseCase`).
- **Data Layer**: DTO models (`OTTDataModelClass`), `LocalDataSource` reading mock JSON, mappers, and repository implementations (`DomainRepoImpl`, `AnalyticsRepoImpl`).

### Tech Stack
- **Kotlin**
- **XML Layouts** (ConstraintLayout, Material Components)
- **Jetpack Navigation**
- **Coroutines & StateFlow**
- **Android Media3 / ExoPlayer**
- **Picasso** (Image loading)
- **Gson** (JSON parsing)

---

## Replacing Mock Data with Real API

The data layer uses an interface abstraction (`ContentDataSource`):

- `ContentDataSource`
  - `LocalDataSource` (Reads local asset JSON)
  - `RemoteDataSource` (Can be implemented with Retrofit/Ktor)

To swap the mock data with a live backend API:
1. Implement `ContentDataSource` with Retrofit in a new `RemoteDataSource` class.
2. Inject/pass `RemoteDataSource` into `DomainRepoImpl`.

No changes are required in ViewModels, UseCases, Domain models, or UI code.

---

## How to Build and Run

1. Open project in **Android Studio**.
2. Sync Gradle files.
3. Select `app` run configuration and deploy to an emulator or physical device.
