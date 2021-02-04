# ListDetail
A simple list-detail android application

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.3.72-blue.svg)](https://kotlinlang.org)
[![AGP](https://img.shields.io/badge/AGP-3.6.3-blue?style=flat)](https://developer.android.com/studio/releases/gradle-plugin)
[![Gradle](https://img.shields.io/badge/Gradle-5.6.4-blue?style=flat)](https://gradle.org)

[![codebeat badge](https://codebeat.co/badges/7f632064-0be5-450f-b29f-f0e1460582ab)](https://codebeat.co/projects/github-com-igorwojda-android-showcase-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a7ef0746703e4c81b0e4af2c46e2885e)](https://app.codacy.com/app/igorwojda/android-showcase?utm_source=github.com&utm_medium=referral&utm_content=igorwojda/android-showcase&utm_campaign=Badge_Grade_Dashboard)
[![CodeFactor](https://www.codefactor.io/repository/github/igorwojda/android-showcase/badge)](https://www.codefactor.io/repository/github/igorwojda/android-showcase)


A simple List-Detail sample project that presents a modern, 2020 approach to
[Android](https://en.wikipedia.org/wiki/Android_(operating_system)) application development with up to date tech-stack.

The goal of the project is to demonstrate best practices by using up to date tech-stack and presenting modern Android application
[Architecture](#architecture) that is monolithic, scalable, maintainable, and testable.

This project is being maintained to match current industry standards. Please check [CONTRIBUTING](CONTRIBUTING.md) page if you want to help.

## Project characteristics

This project brings to table set of best practices, tools, and solutions:

* 100% [Kotlin](https://kotlinlang.org/)
* Modern architecture (Model-View-ViewModel, Model-View-Intent)
* [Android Jetpack](https://developer.android.com/jetpack)
* Reactive UI
* Testing (Unit, UI)
* Material design

## Tech-stack

<img src="misc/image/application_anim.gif" width="336" align="right" hspace="20">

Min API level is set to [`21`](https://android-arsenal.com/api?level=21), so the presented approach is suitable for over
[85% of devices](https://developer.android.com/about/dashboards) running Android. This project takes advantage of many
popular libraries and tools of the Android ecosystem. Most of the libraries are in the stable version unless there is a
good reason to use non-stable dependency.

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations
    * [OkHttp](https://square.github.io/okhttp/) - networking. Known to be lighter than Retrofit.
    * [Jetpack](https://developer.android.com/jetpack)
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
  *   [Glide](https://github.com/bumptech/glide) - image loading library with better memory management for pretty large list
* Architecture
    * Clean Architecture (at module level)
    * MVVM + MVI (presentation layer)
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation), [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) plugin)
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit](https://junit.org/junit4/))
    * [Mockk](https://mockk.io/)
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

### Design decisions

Read related articles to have a better understanding of underlying design decisions and various trade-offs.

## What this project does not cover?

The interface of the app utilizes some of the modern material design components, however, is deliberately kept simple to
focus on application architecture.

## Getting started

There are a few ways to open this project.

### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Enter `https://github.com/SunnyBe/ListDetail.git` into URL field

### Command-line + Android Studio

1. Run `git clone https://github.com/igorwojda/android-showcase.git` to clone project
2. Go to `Android Studio` -> `File` -> `Open` and select cloned directory
3. Provide `APP_ID="<value goes here>"` in the `gradle.properties`

## Inspiration

This is project is a sample, to inspire you and should handle most of the common cases, but please take a look at
additional resources.

### Cheat sheet

* [Android Ecosystem Cheat Sheet](https://github.com/igorwojda/android-ecosystem-cheat-sheet) - board containing 200+ most important tools
* [Kotlin Coroutines - Use Cases on Android](https://github.com/LukasLechnerDev/Kotlin-Coroutine-Use-Cases-on-Android) - most popular coroutine usages

## Known issues
- `ktlint` `import-ordering` rule conflicts with IDE default formatting rule, so it have to be [disabled](.editorconfig)
- False positive "Unused symbol" for a custom Android application class referenced in AndroidManifest.xml file ([Issue](https://youtrack.jetbrains.net/issue/KT-27971))
- False positive "Function can be private" ([Issue](https://youtrack.jetbrains.com/issue/KT-33610))
- Unit tests are running in IDE but fail after running gradle task because of missing Agrs class ([Issue](https://issuetracker.google.com/issues/139242292))

## Contribute

Want to contribute? Check our [Contributing](CONTRIBUTING.md) docs.

## Author

[![Follow me](https://pixabay.com/images/id-1606916/)](https://twitter.com/sunday_nectar)

[![Follow me](https://img.shields.io/twitter/follow/sunday_nectar?style=social)](https://twitter.com/sunday_nectar)

## License
```
MIT License

Copyright (c) 2020 Sunday Ndu

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
