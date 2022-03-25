package com.gustavofc97.data.di

import com.google.gson.GsonBuilder
import com.gustavofc97.data.network.NetworkClient
import com.gustavofc97.data.network.NetworkClientImpl
import com.gustavofc97.data.network.PostServices
import com.gustavofc97.data.utlis.BASE_URL
import com.gustavofc97.data.utlis.CONNECT_TIMEOUT
import com.gustavofc97.data.utlis.READ_TIMEOUT
import com.gustavofc97.data.utlis.WRITE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providerGsonConverter(): GsonConverterFactory {
        val gson = GsonBuilder()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideNetworkClient(): NetworkClient {
        return NetworkClientImpl()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    @Singleton
    fun provideBaseRetrofit(
        @Named("BASE_URL")
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    @Reusable
    fun providePostServicesAPI(retrofit: Retrofit): PostServices =
        retrofit.create(PostServices::class.java)
}
