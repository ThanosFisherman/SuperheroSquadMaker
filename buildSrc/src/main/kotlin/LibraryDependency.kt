import kotlin.reflect.full.memberProperties

object LibraryVersion {

    //Core Versions
    const val koinVersion = "3.0.1-beta-2"
    const val rxJavaVersion = "2.2.19"
    const val rxAndroidVersion = "2.1.1"
    const val rxKotlinVersion = "2.4.0"
    const val timberVersion = "4.7.1"
    const val coroutinesVersion = "1.4.2"
    const val jwtVersion = "2.0.0"

    //UI Versions
    const val androidxVersion = "1.2.0"
    const val lifecycleVersionX = "2.3.1"
    const val picassoVersion = "2.71828"
    const val constraintLayoutVersion = "2.1.0-beta01"
    const val recyclerViewVersion = "1.1.0"
    const val materialVersion = "1.4.0-alpha01"
    const val mayIVersion = "2.4.0"
    const val progressButtonVersion = "2.1.0"
    const val flowBindingsVersion = "0.12.0"
    const val pagingVersion = "2.1.2"
    const val paging3Version = "3.0.0-beta03"
    const val coilVersion = "1.1.1"
    //const val glideVersion = "4.11.0"

    //Network Versions
    const val retrofit2Version = "2.9.0"
    const val okioVersion = "2.10.0"
    const val okhttpLoggingVersion = "4.9.1"
    const val moshiVersion = "1.11.0"
    const val networkResponseAdapterVersion = "4.1.0"

    //Bluetooth Versions
    const val blueFlowVersion = "1.1.0"

    //Persistence Versions
    const val prefsVersion = "1.1.1"
    const val roomVersion = "2.2.6"

    //Firebase Versions
    const val firebasePerformanceVersion = "19.0.7"
    const val firebaseCrashlyticsVersion = "2.10.1"
    const val firebaseAnalyticsVersion = "17.4.2"

}

object CoreDependency {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${CoreVersion.KOTLIN}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"
    const val koin = "io.insert-koin:koin-core:${LibraryVersion.koinVersion}"
    const val koinAndroid = "io.insert-koin:koin-android:${LibraryVersion.koinVersion}"
    const val koinAndroidExp = "io.insert-koin:koin-android-ext:${LibraryVersion.koinVersion}"
    const val koinWorkManager = "io.insert-koin:koin-androidx-workmanager:${LibraryVersion.koinVersion}"
    const val timber = "com.jakewharton.timber:timber:${LibraryVersion.timberVersion}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${LibraryVersion.moshiVersion}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${LibraryVersion.moshiVersion}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibraryVersion.coroutinesVersion}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersion.coroutinesVersion}"
    const val listPageAdapter = "androidx.paging:paging-runtime-ktx:${LibraryVersion.paging3Version}"

    fun getAll() = CoreDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object UiDependency {

    const val androidX = "androidx.appcompat:appcompat:${LibraryVersion.androidxVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${LibraryVersion.constraintLayoutVersion}"
    const val viewModelLifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.lifecycleVersionX}"
    const val liveDataLifecycle ="androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.lifecycleVersionX}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${LibraryVersion.recyclerViewVersion}"
    const val navigationUI = "androidx.navigation:navigation-fragment-ktx:${CoreVersion.NAVIGATION}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${CoreVersion.NAVIGATION}"
    const val material = "com.google.android.material:material:${LibraryVersion.materialVersion}"
    const val mayI = "com.thanosfisherman.mayi:mayi:${LibraryVersion.mayIVersion}"
    const val viewFlowBindings = "io.github.reactivecircus.flowbinding:flowbinding-android:${LibraryVersion.flowBindingsVersion}"
    const val progressButton = "com.github.razir.progressbutton:progressbutton:${LibraryVersion.progressButtonVersion}"
    const val coil = "io.coil-kt:coil:${LibraryVersion.coilVersion}"

    fun getAll() = UiDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object NetworkDependency {

    const val retrofit = "com.squareup.retrofit2:retrofit:${LibraryVersion.retrofit2Version}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${LibraryVersion.retrofit2Version}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.okhttpLoggingVersion}"
    const val okio = "com.squareup.okio:okio:${LibraryVersion.okioVersion}"
    const val networkResponseAdapter = "com.github.haroldadmin:NetworkResponseAdapter:${LibraryVersion.networkResponseAdapterVersion}"

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

object FirebaseDependency {

    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${LibraryVersion.firebaseCrashlyticsVersion}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${LibraryVersion.firebaseAnalyticsVersion}"
    const val firebasePerformance = "com.google.firebase:firebase-perf:${LibraryVersion.firebasePerformanceVersion}"

    fun getAll() = FirebaseDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}
