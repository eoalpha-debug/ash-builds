package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * DTOs da tier list pública do HOK Pro (validada por pro players brasileiros).
 */
@JsonClass(generateAdapter = true)
data class HoKProTierListResponse(
    @Json(name = "tierlist") val tierList: Map<String, Map<String, List<String>>> = emptyMap()
)

interface HoKProApi {
    @GET("api/tierlist")
    suspend fun getTierList(): HoKProTierListResponse
}

object RemoteModule {
    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()
    }

    val hoKProApi: HoKProApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://hokpro.gg/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(HoKProApi::class.java)
    }
}
