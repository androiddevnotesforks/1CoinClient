<h1 align="center">1Coin</h1>

![Preview](https://github.com/1Coin-FinanceTracker/1CoinClient/blob/develop/documentation/images/preview.png)

<div align="center">
  <strong>Simple money manager</strong>
</div>
<div align="center">
  It's superfast, simple and lightweight.
</div>

<br />

<div align="center">
  <!-- Contributors -->
  <a href="https://github.com/1Coin-FinanceTracker/1CoinClient/graphs/contributors" alt="Contributors">
        <img src="https://img.shields.io/github/contributors/VitalyPeryatin/FinanceTracker" /></a>
  <!-- Build status -->
  <a href="https://github.com/1Coin-FinanceTracker/1CoinClient/actions/workflows/android.yml" alt="Build status">
        <img src="https://img.shields.io/github/actions/workflow/status/VitalyPeryatin/FinanceTracker/android.yml" /></a>
  <!-- Telegram chat -->
  <a href="https://t.me/+FFK1aCS6uJs1NTBi">
        <img src="https://img.shields.io/badge/Telegram-2CA5E0?logo=telegram&logoColor=white"
            alt="chat on Telegram"></a>
</div>

<div align="center">
  <h3>
    <a href="https://github.com/1Coin-FinanceTracker/1CoinClient/blob/develop/CONTRIBUTING.md">
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
    <a href="https://github.com/1Coin-FinanceTracker/1CoinClient/graphs/contributors">
      contributors
    </a>
  </sub>
</div>

## Table of Contents
- [Support Team](#support-team)
- [Features](#features)
- [Tech stack](#tech-stack)
- [How to build](#how-to-build)
- [Find out more](#find-out-more)
- [Download](#download)

## Support Team
- [Buy me a coffee](https://www.buymeacoffee.com/1coin)
- [Boosty](https://boosty.to/1coin)
- [Patreon](https://patreon.com/1Coin)
- [BTC address](https://explorer.btc.com/btc/address/bc1qjszqdrj3rag97qz9cayac7hrz5l3t4gppk2q7h)
- [ETH address](https://etherscan.io/address/0x1a7D0B4758355469c9026F067FCBeF7eeC79BC17)

## Features

| **Manage accounts** | **Manage categories** |
|---------------------|-----------------------|
| [<img src="documentation/images/demo/manage_accounts.gif" width="200" height = "431" />](documentation/images/demo/manage_accounts.gif) | [<img src="documentation/images/demo/manage_categories.gif" width="200" height = "431" />](documentation/images/demo/manage_categories.gif) |

| **Manage transactions** | **View analytics** |
|-------------------------|--------------------|
| [<img src="documentation/images/demo/manage_transactions.gif" width="200" height = "431" />](documentation/images/demo/manage_transactions.gif) | [<img src="documentation/images/demo/analytics.gif" width="200" height = "431" />](documentation/images/demo/analytics.gif) |

## Tech stack
- UI
  - [Compose Multiplatform](https://github.com/JetBrains/compose-jb) - UI (Android + Desktop)
  - [LGoodDatePicker](https://github.com/LGoodDatePicker/LGoodDatePicker) - DatePicker on Swing (Desktop)
  - [Odyssey](https://github.com/AlexGladkov/Odyssey) - Navigation
  - [Koalaplot](https://github.com/KoalaPlot/koalaplot-core) - Charts
  - [MokoResources](https://github.com/icerockdev/moko-resources) - Resources: strings, colors, images, etc
  - [Lottie](https://airbnb.io/lottie/#/) - Vector animations
- Common
  - [KViewModel](https://github.com/adeo-opensource/kviewmodel--mpp) - Shared ViewModel
  - [Firebase](https://firebase.google.com) - Crashlytics
  - [GoogleServices](https://developers.google.com/android/guides/setup) - Google Authorization
  - [UUID](https://github.com/benasher44/uuid) - UUID generation
  - [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime) - Date and Time
  - [ImmutableCollections](https://github.com/Kotlin/kotlinx.collections.immutable) - Immutable collections by JetBrains
  - [ComposeImageLoader](https://github.com/qdsfdhvh/compose-imageloader) - Image loader for Compose (Android + Desktop)
  - [ArithmeticEvaluator](https://github.com/murzagalin/multiplatform-expressions-evaluator) - Calculates mathematical expressions
  - [BigNum](https://github.com/ionspin/kotlin-multiplatform-bignum) - Processing large numbers
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

## Find out more
- In our public [Notion page](https://1coin.notion.site/Home-702321932efa49ffa9571c8a54757dd1) you can find:
  - Project roadmap 🛤️
  - How to become a contributor to a project 🤝🏻
  - How to financially thank the project 🙏🏻

## Download
- Windows: -
- MacOS: 
  - [1Coin.app.zip (Binary)](https://github.com/1Coin-FinanceTracker/1CoinClient/releases)
- Linux: -
- Android:
  - [1Coin (PlayMarket)](https://play.google.com/store/apps/details?id=com.finance_tracker.finance_tracker)
  - [1Coin.apk (Binary)](https://github.com/1Coin-FinanceTracker/1CoinClient/releases)
- iOS: _In developing_
