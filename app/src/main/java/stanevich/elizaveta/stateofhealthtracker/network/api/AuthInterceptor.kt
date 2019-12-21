package stanevich.elizaveta.stateofhealthtracker.network.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val accessToken: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
//            "Authorization", "key $accessToken"
            "Authorization",
            "Basic dXNlcjpwYXNzd29yZA==" //TODO change it when access token will work
        ).build()

        Log.d("MYNETWORK", request.toString())
        return chain.proceed(request)
    }
}