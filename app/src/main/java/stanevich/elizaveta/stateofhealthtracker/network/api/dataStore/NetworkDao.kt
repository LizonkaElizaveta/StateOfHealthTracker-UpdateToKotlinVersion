package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

interface NetworkDao<out T : Any> {

    fun findAll(): List<T>

    fun clear()
}

