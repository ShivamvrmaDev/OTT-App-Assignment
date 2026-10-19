# OTT Video Player - Android Application

An OTT-style Android application built with **Kotlin**, **Clean Architecture**, **MVVM**, **Android Jetpack**, **Media3 / ExoPlayer**, and **XML Layouts**.

---

## 📥 APK Download for Testing

You can download and test the pre-compiled Debug APK directly from this repository:
- 📲 **[Download Test APK (app-debug.apk)](apk/app-debug.apk)**

---

## 📱 Application Features

- **OTT Home Screen**:
  - **Hero Banner**: Featured title with background image, title, description, and "Watch Now" / "View Details" CTA.
  - **Content Rails**: Multiple horizontally scrollable `RecyclerView` rails ("Trending Now", "Recommended for You").
  - **Dynamic Population**: Populated asynchronously from mock JSON via Clean Architecture layers.
- **Content Detail Screen**:
  - Displays selected item's high-res thumbnail, title, description, and a prominent "Watch Now" button launching playback.
- **Video Player**:
  - Built with **Android Media3 / ExoPlayer** supporting Play, Pause, Seek, and Progress tracking.
  - Full lifecycle management (`onPause`, `onResume`, `onStop`, `onDestroyView`).
  - Configured with `DefaultHttpDataSource.Factory` User-Agent headers to prevent HTTP 403 network errors.
- **Player Events & Analytics Pipeline**:
  - Full separation of concerns for tracking player events (`Player` ➔ `PlayerEventHandler` ➔ `ViewModel` ➔ `AnalyticsRepository` ➔ Logcat).
  - Captures and logs:
    - `PLAYER_READY`
    - `PLAYER_STARTED`
    - `PLAYER_PAUSED`
    - `PLAYER_SEEKED`
    - `BUFFERING_STARTED`
    - `BUFFERING_ENDED`
    - `PLAYER_COMPLETED`
    - `PLAYER_ERROR`

---

## 📐 Architecture Overview

The app strictly adheres to **Clean Architecture** and **MVVM** principles with three distinct layers:

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                      │
│   HomeFragment / ItemDetailsFrag / VideoPlayerFrag          │
│   MyViewModel / VideoPlayerViewModel                        │
│   UiState<T> (Loading, Success, Empty, Error)               │
│   PlayerEventHandler                                        │
└──────────────────────────────┬──────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                        Domain Layer                         │
│   DomainItem / DomainCategory / DomainOTTDataModel          │
│   PlayerEvent (Sealed Class)                                │
│   GetHomeContentUseCase / LogPlayerEventUseCase             │
│   DomainRepository / AnalyticsRepository (Interfaces)       │
└──────────────────────────────▲──────────────────────────────┘
                               │
                               │ Implements
┌──────────────────────────────┴──────────────────────────────┐
│                         Data Layer                          │
│   DomainRepoImpl / AnalyticsRepoImpl                        │
│   ContentDataSource (Interface) / LocalDataSource           │
│   OTTDataDto / CategoryDto / ItemDto                        │
│   OTTDataMapper (toDomain())                                │
└─────────────────────────────────────────────────────────────┘
```

---

## 📂 Project Structure

```
com.shivam.ottvideoplayer/
├── data/
│   ├── data_resource/
│   │   ├── ContentDataSource.kt        // Data Source interface
│   │   └── LocalDataSource.kt          // Mock JSON Data Source
│   ├── model/                          // DTOs & Mappers
│   │   ├── Item.kt
│   │   ├── Category.kt
│   │   └── OTTDataModelClass.kt
│   └── repostiory/                     // Repository implementations
│       ├── DomainRepoImpl.kt
│       └── AnalyticsRepoImpl.kt
├── domain/
│   ├── domain_model/                   // Pure Kotlin Domain Entities
│   │   ├── DomainItem.kt
│   │   ├── DomainCategory.kt
│   │   ├── DomainOTTDataModel.kt
│   │   └── PlayerEvent.kt
│   ├── repostiory/                     // Repository Contracts
│   │   ├── DomainRepository.kt
│   │   └── AnalyticsRepository.kt
│   └── use_case/                       // Business Use Cases
│       ├── GetHomeContentUseCase.kt
│       └── LogPlayerEventUseCase.kt
└── presentation/
    ├── common/
    │   └── UiState.kt                  // UI State Wrapper (Loading, Success, Error, Empty)
    ├── player/
    │   └── PlayerEventHandler.kt       // Media3 Listener & Event Translator
    ├── ui/
    │   ├── frgaments/
    │   │   ├── HomeFrag.kt
    │   │   ├── ItemDetailsFrag.kt
    │   │   └── VideoPlayerFrag.kt
    │   └── utils/
    │       ├── DataAdapter.kt          // ListAdapter + DiffUtil for Content Rails
    │       └── MyViewModel.kt          // ViewModel exposing StateFlow
    └── viewmodel/
        └── VideoPlayerViewModel.kt
```

---

## 🛠️ Tech Stack & Libraries Used

- **Language**: Kotlin
- **UI Framework**: XML Layouts (ConstraintLayout, ScrollView, Material3 Components)
- **Architecture**: Clean Architecture + MVVM
- **Jetpack Components**:
  - `ViewModel` & `AndroidViewModel`
  - `Navigation Component` (`NavHostFragment`, `findNavController()`)
  - `ViewBinding`
  - `Lifecycle` & `lifecycleScope`
- **Asynchronous Flow**: Kotlin Coroutines & `StateFlow`
- **Video Player**: Android `Media3 / ExoPlayer` (`1.11.1`) & `PlayerView`
- **Image Loading**: Picasso (`2.8`)
- **JSON Parsing**: Gson (`2.10.1`)

---

## 🎬 Player Event Handling Approach

Player event tracking is decoupled from Fragments using a clean **event-driven pipeline**:

1. **`PlayerEventHandler`**: Implements `Player.Listener` on Media3 ExoPlayer. It monitors playback state changes, pause/play toggles, seek discontinuities, buffering, and errors.
2. **`PlayerEvent`**: Sealed class representing domain player events (`PlayerReady`, `PlaybackStarted`, `PlaybackPaused`, `SeekPerformed`, `BufferingStarted`, `BufferingEnded`, `PlaybackCompleted`, `PlaybackError`).
3. **`VideoPlayerViewModel`**: Receives `PlayerEvent` items and delegates them to `LogPlayerEventUseCase`.
4. **`AnalyticsRepository` (`AnalyticsRepoImpl`)**: Formats and logs the event cleanly to Logcat (`[PLAYER_EVENT_ANALYTICS] -> PLAYER_STARTED`).

---

## 🔄 API-Ready Design (Replacing Mock JSON with Real API)

The data layer is designed around the `ContentDataSource` abstraction:

```
ContentDataSource (Interface)
     ├── LocalDataSource (Current: Reads JSON from assets)
     └── RemoteDataSource (Future: Retrofit / Ktor API calls)
```

To replace the mock data source with a real backend API in the future:
1. Create a `RemoteDataSource : ContentDataSource` using Retrofit.
2. Pass `RemoteDataSource` into `DomainRepoImpl`.
3. **Zero changes** are required in ViewModels, UseCases, Domain Models, or UI Fragments!

---

## 🚀 How to Build and Run

1. Open the project in **Android Studio** (Hedgehog / Iguana / Jellyfish or newer).
2. Sync the project with Gradle files (`File -> Sync Project with Gradle Files`).
3. Connect an Android Emulator or physical device (Android 7.0 / API 24+ recommended).
4. Run the app (`Shift + F10` or click **Run 'app'**).
