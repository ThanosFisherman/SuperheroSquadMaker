import kotlin.reflect.full.memberProperties

object ModuleDependency {
    // All consts are accessed via reflection
    const val APP = ":app"
    const val DOMAIN = ":domain"
    const val PRESENTATION = ":presentation"
    const val DATA_PERSISTENCE = ":data_persistence"
    const val DATA_NETWORK = ":data_network"

    fun getAllModules() = ModuleDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()

    fun getAppModuleDependencies() = setOf(PRESENTATION, DOMAIN, DATA_NETWORK, DATA_PERSISTENCE)

}
