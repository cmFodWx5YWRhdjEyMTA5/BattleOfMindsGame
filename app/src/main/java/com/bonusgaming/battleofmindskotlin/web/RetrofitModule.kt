package com.bonusgaming.battleofmindskotlin.web

import com.bonusgaming.battleofmindskotlin.BuildConfig
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}