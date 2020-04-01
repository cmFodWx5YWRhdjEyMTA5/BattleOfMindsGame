package com.bonusgaming.battleofmindskotlin.base_web_impl.di.module

import com.bonusgaming.battleofmindskotlin.base_web_api.StickerApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_impl.BuildConfig
import com.bonusgaming.battleofmindskotlin.base_web_impl.StickersApiRetrofit
import com.bonusgaming.battleofmindskotlin.base_web_impl.adapter.ToUrlStickerAdapter
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import com.google.firebase.auth.FirebaseAuth
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

@Module
open class WebModule : WebApi {

    private lateinit var retrofit: Retrofit

    //for debug
    private fun addLogging(clientBuilder: OkHttpClient.Builder) {
        val loggInterceptor = HttpLoggingInterceptor()
        loggInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggInterceptor)
    }

    //open for test
    open fun getBaseUrlApi(): String {
        return BuildConfig.STORAGE_BASE_URL
    }

    private fun getRetrofitClient(): Retrofit {
        if (::retrofit.isInitialized) return retrofit

        val okHttpClientClientBuilder = OkHttpClient.Builder()

        //for debug
        addLogging(okHttpClientClientBuilder)

        okHttpClientClientBuilder.interceptors().add(Interceptor {
            val request = it.request()
            val url =
                    request.url.newBuilder()
                            .addQueryParameter("key", BuildConfig.STORAGE_API_KEY)
                            .build()

            val newRequest = request.newBuilder().url(url).build()

            it.proceed(newRequest)
        })
        val okHttpClientClient = okHttpClientClientBuilder.build()

        return Retrofit.Builder()
                .baseUrl(getBaseUrlApi())
                .client(okHttpClientClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().also {
                    retrofit = it
                }
    }

    //провайдим Picasso на слуйчай изменения конфигурации Picasso, да и классы не должны знать о способе создания Picasso
    @PerFacade
    @Provides
    override fun providePicasso(): Picasso {
        return Picasso.get()
    }

    @PerFacade
    @Provides
    override fun provideStickerApi(): StickerApi {
        getRetrofitClient().also {
            val result: StickersApiRetrofit = it.create(StickersApiRetrofit::class.java)
            return ToUrlStickerAdapter(result)
        }
    }

    @PerFacade
    @Provides
    override fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}