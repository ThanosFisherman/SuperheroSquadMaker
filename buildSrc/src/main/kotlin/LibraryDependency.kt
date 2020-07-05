import kotlin.reflect.full.memberProperties

private object LibraryVersion {

    //Core Versions
    const val koinVersion = "2.1.5"
    const val rxJavaVersion = "2.2.19"
    const val rxAndroidVersion = "2.1.1"
    const val rxKotlinVersion = "2.4.0"
    const val timberVersion = "4.7.1"
    const val coroutinesVersion = "1.3.7"
    const val jwtVersion = "2.0.0"

    //UI Versions
    const val androidxVersion = "1.1.0"
    const val lifecycleVersionX = "2.2.0"
    const val picassoVersion = "2.71828"
    const val constraintLayoutVersion = "2.0.0-beta6"
    const val recyclerViewVersion = "1.1.0"
    const val materialVersion = "1.2.0-beta01"
    const val mayIVersion = "2.3.0"
    const val progressButtonVersion = "2.1.0"
    const val flowBindingsVersion = "0.12.0"
    const val circleImageView = "3.1.0"

    //Network Versions
    const val retrofit2Version = "2.9.0"
    const val okioVersion = "2.5.0"
    const val okhttpLoggingVersion = "4.7.2"
    const val moshiVersion = "1.9.2"

    //Bluetooth Versions
    const val blueFlowVersion = "1.1.0"

    //Persistence Versions
    const val prefsVersion = "1.1.1"
    const val roomVersion = "2.2.5"

    //Firebase Versions
    const val firebasePerformanceVersion = "19.0.7"
    const val firebaseCrashlyticsVersion = "2.10.1"
    const val firebaseAnalyticsVersion = "17.4.2"

}

object CoreDependency {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${CoreVersion.KOTLIN}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"
    const val rxjava2 = "io.reactivex.rxjava2:rxjava:${LibraryVersion.rxJavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${LibraryVersion.rxAndroidVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${LibraryVersion.rxKotlinVersion}"
    const val koin = "org.koin:koin-android:${LibraryVersion.koinVersion}"
    const val koinVM = "org.koin:koin-androidx-viewmodel:${LibraryVersion.koinVersion}"
    const val koinScope = "org.koin:koin-androidx-scope:${LibraryVersion.koinVersion}"
    const val timber = "com.jakewharton.timber:timber:${LibraryVersion.timberVersion}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${LibraryVersion.moshiVersion}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${LibraryVersion.moshiVersion}"
    const val jwt = "com.auth0.android:jwtdecode:${LibraryVersion.jwtVersion}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibraryVersion.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersion.coroutinesVersion}"
    const val blueFLow = "io.github.thanosfisherman.blueflow:blueflow:${LibraryVersion.blueFlowVersion}"

    fun getAll() = CoreDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object UiDependency {

    const val androidX = "androidx.appcompat:appcompat:${LibraryVersion.androidxVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${LibraryVersion.constraintLayoutVersion}"
    const val viewModelLiveData = "androidx.lifecycle:lifecycle-extensions:${LibraryVersion.lifecycleVersionX}"
    const val reactiveStreamsLiveData = "androidx.lifecycle:lifecycle-reactivestreams:${LibraryVersion.lifecycleVersionX}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.lifecycleVersionX}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${LibraryVersion.recyclerViewVersion}"
    const val navigationUI = "androidx.navigation:navigation-fragment-ktx:${CoreVersion.NAVIGATION}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${CoreVersion.NAVIGATION}"
    const val picasso = "com.squareup.picasso:picasso:${LibraryVersion.picassoVersion}"
    const val material = "com.google.android.material:material:${LibraryVersion.materialVersion}"
    const val mayI = "com.thanosfisherman.mayi:mayi:${LibraryVersion.mayIVersion}"
    const val viewFlowBindings = "io.github.reactivecircus.flowbinding:flowbinding-android:${LibraryVersion.flowBindingsVersion}"
    const val progressButton = "com.github.razir.progressbutton:progressbutton:${LibraryVersion.progressButtonVersion}"
    const val circleImageView = "de.hdodenhof:circleimageview:${LibraryVersion.circleImageView}"

    fun getAll() = UiDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object NetworkDependency {

    const val retrofit = "com.squareup.retrofit2:retrofit:${LibraryVersion.retrofit2Version}"
    const val retrofitRxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${LibraryVersion.retrofit2Version}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${LibraryVersion.retrofit2Version}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.okhttpLoggingVersion}"
    const val okio = "com.squareup.okio:okio:${LibraryVersion.okioVersion}"
    const val networkResponseAdapter = "com.github.haroldadmin:NetworkResponseAdapter:4.0.1"

    fun getAll() = NetworkDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object PersistenceDependency {

    const val sharedPrefs = "androidx.preference:preference-ktx:${LibraryVersion.prefsVersion}"
    const val roomDb = "androidx.room:room-runtime:${LibraryVersion.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${LibraryVersion.roomVersion}"
    fun getAll() = PersistenceDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object BluetoothDependency {

    //const val blueFLow = "io.github.thanosfisherman.blueflow:blueflow:${LibraryVersion.blueFlowVersion}"

    fun getAll() = NetworkDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object FirebaseDependency {

    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${LibraryVersion.firebaseCrashlyticsVersion}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${LibraryVersion.firebaseAnalyticsVersion}"
    const val firebasePerformance = "com.google.firebase:firebase-perf:${LibraryVersion.firebasePerformanceVersion}"

    fun getAll() = FirebaseDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}
