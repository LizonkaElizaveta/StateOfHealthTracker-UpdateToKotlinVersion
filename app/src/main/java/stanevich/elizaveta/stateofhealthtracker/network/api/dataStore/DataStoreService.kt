package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import retrofit2.Response
import retrofit2.http.GET
import stanevich.elizaveta.stateofhealthtracker.network.api.RetrofitFactory.Companion.retrofit
import stanevich.elizaveta.stateofhealthtracker.network.dto.IsAliveResponse

interface DataStoreService {
    companion object DataStoreAPI {
        val dataStoreService: DataStoreService by lazy {
            retrofit.create(
                DataStoreService::class.java
            )
        }
    }

    @GET("/alive_checker")
    suspend fun isAlive(): Response<IsAliveResponse>
}

