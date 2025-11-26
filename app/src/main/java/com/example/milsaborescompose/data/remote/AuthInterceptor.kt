package com.example.milsaborescompose.data.remote

import com.example.milsaborescompose.data.repository.SessionRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionRepository: SessionRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // No añadir token a las rutas de login/registro
        if (request.url.encodedPath.contains("api/auth/login") || 
            request.url.encodedPath.contains("api/auth/register")) {
            return chain.proceed(request)
        }

        val session = runBlocking { sessionRepository.getSession().firstOrNull() }
        val token = session?.token

        return if (token != null) {
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }
}
