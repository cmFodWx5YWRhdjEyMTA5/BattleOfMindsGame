package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.os.Bundle
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import androidx.test.core.app.ActivityScenario.launch
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.MainActivityView

@RunWith(RobolectricTestRunner::class)
class LoadingAssetsFragmentTest {

    @Test
    fun `should show download text`(){
        val fragmentArgs = Bundle()

        val scenario = launch(MainActivityView::class.java)
    }
}