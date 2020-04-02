package com.bonusgaming.battleofmindskotlin.features.loading.shadows

import android.content.Context
import android.util.AttributeSet
import com.bonusgaming.battleofmindskotlin.ui.LoadingAssetsBar
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow.directlyOn
import org.robolectric.shadows.ShadowView

@Implements(LoadingAssetsBar::class)
class LoadingAssetsBarShadow : ShadowView() {

    var perProgress = -1
        private set

    var lastProgress = -1
        private set

    @RealObject
    lateinit var loadingAssetsBar: LoadingAssetsBar

    override fun __constructor__(context: Context?, attributeSet: AttributeSet?, defStyle: Int) {
        super.__constructor__(context, attributeSet, defStyle)
    }



    @Implementation
    protected fun setProgress(value: Int) {
        println("---------- - CALL------------ - ")
        if (perProgress == -1) perProgress = value
        lastProgress = value
        directlyOn(loadingAssetsBar, LoadingAssetsBar::class.java).setProgress(value)
    }
}