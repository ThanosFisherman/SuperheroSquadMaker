import kotlin.reflect.full.memberProperties

object PersistenceKaptDependency {

    const val roomKapt = "androidx.room:room-compiler:${LibraryVersion.roomVersion}"

    fun getAll() = PersistenceKaptDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()
}