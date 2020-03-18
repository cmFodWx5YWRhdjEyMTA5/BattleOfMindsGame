package com.bonusgaming.battleofmindskotlin.di.module

import com.bonusgaming.battleofmindskotlin.BuildConfig
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class WebModule {

    //for debug
    private fun addLogging(clientBuilder: OkHttpClient.Builder) {
        val loggInterceptor = HttpLoggingInterceptor()
        loggInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggInterceptor)
    }

    @Provides
    @Singleton
    fun getRetrofitClient(): Retrofit {
        val okHttpClientClientBuilder = OkHttpClient.Builder()

        //for debug
        addLogging(okHttpClientClientBuilder)

        okHttpClientClientBuilder.interceptors().add(Interceptor {
            val request = it.request()
            val url =
                    request.url().newBuilder()
                            .addQueryParameter("key", BuildConfig.STORAGE_API_KEY)
                            .build()

            val newRequest = request.newBuilder().url(url).build()

            it.proceed(newRequest)
        })
        val okHttpClientClient = okHttpClientClientBuilder.build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.STORAGE_BASE_URL)
                .client(okHttpClientClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
    }

    //провайдим Picasso на слуйчай изменения конфигурации Picasso, да и классы не должны знать о способе создания Picasso
    @Provides
    @Singleton
    fun getPicasso(): Picasso {
        return Picasso.get()
    }
}