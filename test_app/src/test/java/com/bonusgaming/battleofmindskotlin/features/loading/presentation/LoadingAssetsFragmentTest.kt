package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.os.Build
import android.os.Bundle
import com.bonusgaming.battleofmindskotlin.TestApp
import com.bonusgaming.battleofmindskotlin.features.loading.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class,sdk = [Build.VERSION_CODES.O_MR1])
class LoadingAssetsFragmentTest {

    @Test
    fun `should show download text`() {
        val fragmentArgs = Bundle()

//        val scenario = launch(MainActivityView::class.java)
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)
    }
}