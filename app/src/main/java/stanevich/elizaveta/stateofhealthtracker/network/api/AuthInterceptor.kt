package stanevich.elizaveta.stateofhealthtracker.network.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import stanevich.elizaveta.stateofhealthtracker.BuildConfig.API_DEVELOPER_TOKEN

class AuthInterceptor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            "Authorization",
            "Basic $API_DEVELOPER_TOKEN"
        ).build()

        Log.d("SHTRequest", request.toString())
        return chain.proceed(request)
    }
}