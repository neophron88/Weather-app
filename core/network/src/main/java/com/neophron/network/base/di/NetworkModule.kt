package com.neophron.network.base.di

import com.neophron.network.base.BaseUrl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
internal class NetworkModule {

    private fun getMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    internal fun provideBaseUrl(): BaseUrl = BaseUrl

    @Provides
    @Singleton
    internal fun provideRetrofit(baseUrl: BaseUrl): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${baseUrl.url}${baseUrl.apiVersion}")
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(createPathBearerTokenInterceptor(baseUrl))
                    .build()
            ).build()
    }


    private fun createPathBearerTokenInterceptor(url: BaseUrl) = Interceptor { chain ->
        var request = chain.request()
        val newBuilder = request.url.newBuilder()
        val httpUrl = newBuilder.addQueryParameter(url.bearerTokenParam, url.bearerToken).build()
        request = request.newBuilder().url(httpUrl).build()
        return@Interceptor chain.proceed(request)
    }
}