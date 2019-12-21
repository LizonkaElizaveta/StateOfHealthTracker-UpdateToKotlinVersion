package stanevich.elizaveta.stateofhealthtracker.network.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import stanevich.elizaveta.stateofhealthtracker.BuildConfig

class RetrofitFactory {

    companion object {
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        private val okHttpClientBuilder: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN))
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClientBuilder)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.API_ENDPOINT)
            .build()
    }


}