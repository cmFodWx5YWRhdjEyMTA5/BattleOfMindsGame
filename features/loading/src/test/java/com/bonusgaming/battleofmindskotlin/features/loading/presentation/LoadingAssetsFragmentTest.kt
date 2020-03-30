package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import com.bonusgaming.battleofmindskotlin.features.loading.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoadingAssetsFragmentTest {

    @Test
    fun `should show download text`() {
        val fragmentArgs = Bundle()

//        val scenario = launch(MainActivityView::class.java)
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)
    }
}