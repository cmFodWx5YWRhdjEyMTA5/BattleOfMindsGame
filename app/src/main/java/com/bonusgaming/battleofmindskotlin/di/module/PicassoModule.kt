package com.bonusgaming.battleofmindskotlin.di.module

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class PicassoModule {

    @Provides
    @Singleton
    fun getPicasso(context: Context): Picasso {
        val clientBuilder = OkHttpClient.Builder()
        val downloader = OkHttp3Downloader(clientBuilder.build())
        return Picasso.Builder(context).downloader(downloader).build()
    }
}