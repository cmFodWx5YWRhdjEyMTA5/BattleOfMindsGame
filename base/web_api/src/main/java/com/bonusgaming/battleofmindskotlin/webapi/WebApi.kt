package com.bonusgaming.battleofmindskotlin.webapi

import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

interface WebApi {
    fun providePicasso(): Picasso
    fun provideStickerApi(): StickerApi
    fun provideFirebaseAuth(): FirebaseAuth
}
