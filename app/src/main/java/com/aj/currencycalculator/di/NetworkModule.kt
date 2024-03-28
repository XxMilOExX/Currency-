package com.aj.currencycalculator.di

import com.aj.currencycalculator.BuildConfig
import com.aj.currencycalculator.data.network.CurrencyAPI
import com.aj.currencycalculator.util.NetworkConstant.OK_HTTP_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(
        @Named("BaseUrl")
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(
                baseUrl
            ).build()
    }

    @Singleton
    @Provides
    fun providesCurrencyAPI(retrofit: Retrofit): CurrencyAPI {
        return retrofit.create(CurrencyAPI::class.java)
    }

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl(): String {
        return BuildConfig.FIXER_BASE_URL
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("API_KEY") apiKey: String
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                Interceptor { chain ->
                    val original = chain.request()
                    val request: Request =
                        original.newBuilder().addHeader("apikey", apiKey)
                            .method(original.method, original.body).build()
                    chain.proceed(request)
                }
            )
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    @Named("API_KEY")
    fun providesAPIKey(): String {
        return BuildConfig.FIXER_IO_ACCESS_KEY
    }
}
