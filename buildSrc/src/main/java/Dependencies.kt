object Versions {
    const val koin = "3.2.0"
    const val kermit = "1.1.3"
}

object Deps {

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object Kermit {
        const val kermitMain = "co.touchlab:kermit:${Versions.kermit}"
    }

    const val multiplatformSettings = "com.russhwolf:multiplatform-settings:1.0.0-alpha01"
    const val multiplatformSettingsCoroutines = "com.russhwolf:multiplatform-settings-coroutines:1.0.0-alpha01"



    object RealDatabase {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt"
    }

    object JetpackPreviews {
        // Lower-level APIs with support for custom serialization
        const val datasore_okio = "androidx.datastore:datastore-core-okio:1.1.0-dev01"
        // Higher-level APIs for storing values of basic types
        const val datasore = "androidx.datastore:datastore-preferences-core:1.1.0-dev01"

    }

}