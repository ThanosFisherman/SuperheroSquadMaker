import kotlin.reflect.full.memberProperties

object PersistenceKaptDependency {

    const val roomKapt = "androidx.room:room-compiler:${LibraryVersion.roomVersion}"

    fun getAll() = PersistenceKaptDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}

object CoreKaptDependency {
    const val moshiKapt = "com.squareup.moshi:moshi-kotlin-codegen:${LibraryVersion.moshiVersion}"

    fun getAll() = CoreKaptDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}