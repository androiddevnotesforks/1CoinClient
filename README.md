<h1 align="center">1Coin</h1>

![Preview](https://github.com/VitalyPeryatin/1Coin/blob/develop/images/preview.png)

<div align="center">
  <strong>Simple money manager</strong>
</div>
<div align="center">
  It's superfast, simple and lightweight.
</div>

<br />

<div align="center">
  <!-- Contributors -->
  <a href="https://github.com/VitalyPeryatin/FinanceTracker/graphs/contributors" alt="Contributors">
        <img src="https://img.shields.io/github/contributors/VitalyPeryatin/FinanceTracker" /></a>
  <!-- Build status -->
  <a href="https://github.com/VitalyPeryatin/FinanceTracker/actions/workflows/android.yml" alt="Build status">
        <img src="https://img.shields.io/github/actions/workflow/status/VitalyPeryatin/FinanceTracker/android.yml" /></a>
  <!-- Telegram chat -->
  <a href="https://t.me/+FFK1aCS6uJs1NTBi">
        <img src="https://img.shields.io/badge/Telegram-2CA5E0?logo=telegram&logoColor=white"
            alt="chat on Telegram"></a>
</div>

<div align="center">
  <h3>
    <a href="https://github.com/VitalyPeryatin/1Coin/blob/develop/CONTRIBUTING.md">
      Contributing
    </a>
    <span> | </span>
    <a href="https://t.me/+FFK1aCS6uJs1NTBi">
      Chat
    </a>
  </h3>
</div>

<div align="center">
  <sub>Built with ❤︎ by
    <a href="https://github.com/VitalyPeryatin/FinanceTracker/graphs/contributors">
      contributors
    </a>
  </sub>
</div>

## Table of Contents
- [Features](#features)
- [Tech stack](#tech-stack)
- [How to build](#how-to-build)
- [Builds](#builds)

## Features

| **Manage accounts** | **Manage categories** |
|---------------------|-----------------------|
| [<img src="images/demo/manage_accounts.gif" width="200" height = "431" />](images/demo/manage_accounts.gif) | [<img src="images/demo/manage_categories.gif" width="200" height = "431" />](images/demo/manage_categories.gif) |

| **Manage transactions** | **View analytics** |
|-------------------------|--------------------|
| [<img src="images/demo/manage_transactions.gif" width="200" height = "431" />](images/demo/manage_transactions.gif) | [<img src="images/demo/analytics.gif" width="200" height = "431" />](images/demo/analytics.gif) |

## Tech stack
- UI
  - [Compose Multiplatform](https://github.com/JetBrains/compose-jb) - UI (Android + Desktop)
  - [LGoodDatePicker](https://github.com/LGoodDatePicker/LGoodDatePicker) - DatePicker on Swing (Desktop)
  - [Odyssey](https://github.com/AlexGladkov/Odyssey) - Navigation
  - [Koalaplot](https://github.com/KoalaPlot/koalaplot-core) - Charts
- Common
  - [KViewModel](https://github.com/adeo-opensource/kviewmodel--mpp) - Shared ViewModel
  - [Firebase](https://firebase.google.com) - Crashlytics
  - [UUID](https://github.com/benasher44/uuid) - UUID generation
  - [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime) - Date and Time
- Data
  - [Ktor](https://ktor.io/docs/welcome.html) - Network communication
  - [SqlDelight](https://github.com/cashapp/sqldelight) - SQLite database
  - [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) - Serialization
  - [Json](https://mvnrepository.com/artifact/org.json/json/20210307) - Java JSON objects (for storing data in Desktop)
  - [Settings](https://github.com/russhwolf/multiplatform-settings) - Key-value persistent storage
  - [Paging](https://github.com/cashapp/multiplatform-paging) - Paging for multiplatform
- Loggers
  - [Napier](https://github.com/AAkira/Napier) - Common logger
  - [Slf4j](https://www.slf4j.org) - JVM logger (Desktop)
  - [Chucker](https://github.com/ChuckerTeam/chucker) - Network logger (Android)
- Dependency Injection
  - [Koin](https://insert-koin.io) - Dependency injection
- Clean code analyzers
  - [LeakCanary](https://square.github.io/leakcanary) - Memory leaks analysis (Android)
  - [Detekt](https://github.com/detekt/detekt) - Static code analysis
- Analytics
  - [Amplitude](https://www.docs.developers.amplitude.com/getting-started) - General analytics

## How to build
- Android
    - Sync gradle
    - Run Android configuration project
- Desktop
    - Sync gradle
    - Launch <i>'main()'</i> function in <i>'desktop/src/jvmMain/kotlin/com/finance_tracker/finance_tracker/main.kt'</i>

## Builds
- Windows: -
- MacOS: -
- Linux: -
- Android: -
- iOS: <i>In developing</i>
