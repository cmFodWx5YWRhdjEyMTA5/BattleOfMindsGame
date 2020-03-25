package com.bonusgaming.battleofmindskotlin.base_web_api

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

interface WebApi {
    fun providePicasso(): Picasso
    fun provideStickerApi(): StickerApi
    fun provideFirebaseAuth(): FirebaseAuth
}