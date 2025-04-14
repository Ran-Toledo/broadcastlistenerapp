# BroadcastListenerApp

BroadcastListenerApp is an Android application built using Kotlin and Jetpack Compose. It listens to system and custom broadcast events, constructs structured payloads, deduplicates them based on content, and dispatches them to a backend server. It includes support for common system broadcasts such as power connection changes, Bluetooth adapter state, headset plug detection, and airplane mode, along with a custom in-app broadcast.

## Build and Run Instructions

1. **Clone the project** using Git or download the source code.
2. **Open the project** in Android Studio (preferably Arctic Fox or later).
3. **Set the webhook URL and cache size**:
   - Open `Config.kt` located in `com.example.broadcastlistener.data`
   - Set your desired webhook URL (either a [Webhook testing site](https://webhook.site/) or your local backend)
   - Configure cache size settings (see below)

   Example:

   ```kotlin
   object Config {
       var webhookUrl = "http://10.0.2.2:5000"

       const val MAX_CACHE_SIZE = 50
   }
   ```

4. **Build and run the app** on an emulator or physical device.

## Backend Setup

You can use one of the following for receiving and logging event payloads:

### Option A: Webhook Testing Site (Quick Setup)

1. Go to [https://webhook.site](https://webhook.site)
2. Copy the generated unique URL
3. Replace the `backendUrl` in `Config.kt` with this URL

```kotlin
var backendUrl = "https://webhook.site/your-unique-id"
```

### Option B: Local Backend Using Docker

1. Go to the `backend/` folder with:
   - `app.py`: Flask server
   - `Dockerfile`, `docker-compose.yml`, and `requirements.txt`
2. Run the server:

```cmd
docker-compose up --build
```

3. Set `backendUrl` in `Config.kt` to `http://10.0.2.2:5000`

## Testing Deduplication Logic

### Instrumented Tests

These are located in `src/androidTest/`.

#### From Android Studio:
- Open `DeduplicationInstrumentedTest.kt`
- Run individual test methods or the whole class

#### From command line:
```cmd
gradlew.bat connectedAndroidTest
```

Expected output (in Logcat or backend log):
```
Duplicate event skipped: android.bluetooth.adapter.action.STATE_CHANGED
```

## Dependencies

- Android SDK 31+
- Kotlin 1.7+
- Jetpack Compose
- Coroutine libraries (standard Kotlinx)
- Internet permission in `AndroidManifest.xml`
- Instrumentation test runner: `androidx.test.ext:junit:1.1.5`

## Project Structure Summary

- `Config.kt`: Defines the webhook URL and cache limits
- `FirstUseTracker.kt`: Ensures "first use" is sent once per install
- `MainActivity.kt`: UI logic and broadcast registration

### `deduplication/`
- `Deduplicator.kt`: Caches recent event hashes and prevents duplicates
- `DeduplicationStrategy.kt`: Interface for defining deduplication logic
- `DeduplicationStrategies.kt`: Strategies for each event type
- `StrategyRegistry.kt`: Maps actions to strategies

### `events/`
- `CustomEventSender.kt`: Triggers custom broadcasts from the UI
- `EventDispatcher.kt`: Forwards events and applies deduplication
- `EventSender.kt`: Sends events to the backend
- `SystemEvent.kt`: Event model

### `receiver/`
- `BaseReceiver.kt`: broadcast receiver logic
- `EventListener.kt`: Interface for event receivers


## Design Rationale

### Separation of Concerns
Each component (event capturing, dispatching, deduplication, backend transmission) is split into its own package or directory.

### Custom Deduplication Strategies
To ensure that events are not unnecessarily dispatched multiple times, each event type is associated with its own deduplication strategy:

Some broadcasts (like ACTION_POWER_CONNECTED) require only action name for deduplication.

Others (like HEADSET_PLUG or custom events) contain multiple attributes in their payload.

Comparing all attributes every time would be inefficient, so we compute event hashes using strategy-specific keys, making comparisons fast and memory-efficient.

This design allows each strategy to define what “uniqueness” means, keeps Deduplicator independent of event structure and simplifies testing by allowing isolated strategy validation

### Central Configuration
All configurable values (like webhookUrl and deduplication cache size) are centralized in Config.kt.

### First-Use Tracking
The app sends a “first-use” event on initial install using SharedPreferences, while avoiding any UI clutter.

### Tests and Instrumentation
The deduplication logic is verified using instrumented tests, running directly on the Android runtime — eliminating permission issues and ensuring logic-level correctness without needing real broadcasts.

