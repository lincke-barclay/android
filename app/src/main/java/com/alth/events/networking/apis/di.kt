package com.alth.events.networking.apis

import android.content.Context
import com.alth.events.R
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventsApiModule {
    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
    ): Retrofit {
        val contentType = "application/json".toMediaType()
        val client = OkHttpClient()
            .newBuilder()
            .build()
        return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.app_url))
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    fun provideEventApi(
        retrofit: Retrofit,
    ): EventsApi {
        return retrofit.create(EventsApi::class.java)
    }

    @Provides
    fun provideUserApi(
        retrofit: Retrofit,
    ): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    fun provideFriendsApi(
        retrofit: Retrofit,
    ): FriendsApi {
        return retrofit.create(FriendsApi::class.java)
    }
}
