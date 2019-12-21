package stanevich.elizaveta.stateofhealthtracker.network.api.dataStore

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import stanevich.elizaveta.stateofhealthtracker.network.api.RetrofitFactory.Companion.retrofit
import stanevich.elizaveta.stateofhealthtracker.network.dto.DataResponse
import stanevich.elizaveta.stateofhealthtracker.network.dto.IsAliveResponse
import stanevich.elizaveta.stateofhealthtracker.network.dto.SendWrapper

interface DataStoreService {
    companion object DataStore {
        val dataStoreService: DataStoreService by lazy {
            retrofit.create(
                DataStoreService::class.java
            )
        }
    }

    @GET("/alive_checker")
    suspend fun isAlive(): Response<IsAliveResponse>

    @POST("/users_data")
    suspend fun sendUserData(@Body sendWrapper: List<SendWrapper>): Response<DataResponse>

}

