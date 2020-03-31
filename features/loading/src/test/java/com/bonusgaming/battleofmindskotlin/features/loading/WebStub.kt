package com.bonusgaming.battleofmindskotlin.features.loading

import com.bonusgaming.battleofmindskotlin.base_web_api.StickerApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import io.reactivex.Single
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class WebStub : WebApi {
    override fun providePicasso(): Picasso {
        return Mockito.mock(Picasso::class.java)
    }

    override fun provideStickerApi(): StickerApi {
        val stickerApi = Mockito.mock(StickerApi::class.java)
        `when`(stickerApi.getStickers(anyString()))
                .thenReturn(Single.just(getSameUrlStickerListWithSize(100)))
        return stickerApi
    }

    override fun provideFirebaseAuth(): FirebaseAuth {
        return Mockito.mock(FirebaseAuth::class.java)
    }
}