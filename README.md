# KotlinNativeDemo
Spike on compiling Kotlin code into a binary Framework that we can use in iOS code. The cool thing about this is that it is not all-or-nothing. An iOS app can be written 100% in Objective-C/Swift, and use just one class written in Kotlin, exported to a Framework. Swift code calls the Kotlin class as if it were written in Swift. It should be very easy to test the waters without committing to a huge change.

## Areas of interest
### Counting and saving to defaults
[The CountSaver file](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/commoncode/src/commonMain/kotlin/com/gospotcheck/android/mpp/CountSaver.kt) in the common kotlin module has two classes:
 - `Storage`, an interface that has methods to save and retrieve a number
 - `Counter`, which takes an instance of `Storage` as a dependency, and increments or decrements a number from `Storage`, then saves the new number
 
[PlatformStorage](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/commoncode/src/iosMain/kotlin/com/gospotcheck/android/mpp/Storage.kt)  in the common iOS module (written in Kotlin), implements the `Storage` interface with `NSUserDefaults` as the backing storage

[PlatformStorage](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/app/src/main/java/com/gospotcheck/android/kotlinenativedemo/PlatformStorage.kt) in the Android **app** implements the `Storage` interface with `SharedPreferences` as the backing storage

[MainActivity in the Android app](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/app/src/main/java/com/gospotcheck/android/kotlinenativedemo/MainActivity.kt) and [ViewController in the iOS app, written in Swift](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/demoios/KotinNativeDemo2/KotinNativeDemo2/ViewController.swift) new up a `Counter` with the Android and iOS `PlatformStorage` implementations, respectively, then hook up buttons and text view to display and increment/decrement the count.

There are some considerations around importing the Framework itself (see the iOS Framework subheader below), as well as considerations of how we get updated versions of the shared code/Framework to the iOS project seamlessly

### Making an API request
[TimeZoneApi](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/commoncode/src/commonMain/kotlin/com/gospotcheck/android/mpp/TimeZoneApi.kt) in the common kotlin module has two classes or interest:
 - `PositionAndTime`, a simple data class
 - `TimeZoneApi`, a class that takes a `PositionAndTime` as input, makes an API call, then returns the resulting `TimeZoneResponse` to the caller
 
 [AndroidPositionAndTime in the Android app](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/app/src/main/java/com/gospotcheck/android/kotlinenativedemo/AndroidPositionAndTime.kt) is an Android specific implementation of the input the `TimeZoneApi` needs
 
 `SwiftPositionAndTime` in [ViewController in the iOS app (Swift)](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/demoios/KotinNativeDemo2/KotinNativeDemo2/ViewController.swift) is an iOS specific implementation of the input the `TimeZoneApi` needs
 
 [MainActivity in the Android app](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/app/src/main/java/com/gospotcheck/android/kotlinenativedemo/MainActivity.kt) news up an `AndroidPositonAndTime`, passes that to a `TimeZoneApi`, and displays the result in a text view
 
 [ViewController in the iOS app](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/demoios/KotinNativeDemo2/KotinNativeDemo2/ViewController.swift) news up a `SwiftPositionAndTime`, passes that to its instance of `TimeZoneApi`, and displays the result in a `UILabel`
 
 ## iOS Framework
 [The build.gradle file](https://github.com/jeffreyfjohnson/KotlinNativeDemo/blob/master/commoncode/build.gradle) in the common module has the gradle tasks which enable an iOS Framework to be exported from Kotlin code. Right now the only architectures that are supported are Arm64, for iPhone > 5, and X64, for simulator
 
 Also, we [disabled bitcode](https://kotlinlang.org/docs/tutorials/native/mpp-ios-android.html#creating-ios-application) for this XCode project, although I do believe we can make it work with bitcode enabled if that's an issue
