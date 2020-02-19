package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import android.util.Log
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class PicassoModule {

    companion object {
        const val RETRY_TIMEOUT_MILLS = 5000L
    }

    @Provides
    @Singleton
    fun getPicasso(context: Context): Picasso {
        val clientBuilder = OkHttpClient.Builder()

        // clientBuilder.retryOnConnectionFailure(true)

//        clientBuilder.writeTimeout(RETRY_TIMEOUT_MILLS + 1000000, TimeUnit.MILLISECONDS)
//        clientBuilder.readTimeout(RETRY_TIMEOUT_MILLS + 1000000, TimeUnit.MILLISECONDS)
//        clientBuilder.callTimeout(RETRY_TIMEOUT_MILLS + 1000000, TimeUnit.MILLISECONDS)
//        clientBuilder.connectTimeout(RETRY_TIMEOUT_MILLS + 1000000, TimeUnit.MILLISECONDS)
//        clientBuilder.interceptors().add(Interceptor {
//            val request = it.request()
//            var response = it.proceed((request))
//            var count = 0
//            count++
//            response = it.proceed(request)
//            if (!response.isSuccessful) {
//                Log.e("PICASSO", "while $count work ${Thread.currentThread().name}")
//                Thread.sleep(RETRY_TIMEOUT_MILLS)
//                Log.e("PICASSO", "while after sleep")
//                response = it.call().clone().execute()
//            }
//            response
//        })

        val downloader = OkHttp3Downloader(clientBuilder.build())

        return Picasso.Builder(context).downloader(downloader).build()
    }
}