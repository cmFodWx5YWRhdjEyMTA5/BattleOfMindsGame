package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.bonusgaming.battleofmindskotlin.base_ui.LoadingAssetsBar
import com.bonusgaming.battleofmindskotlin.features.loading.R
import com.bonusgaming.battleofmindskotlin.features.loading.TestApp
import com.bonusgaming.battleofmindskotlin.features.loading.shadows.LoadingAssetsViewModelShadow
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class)
class LoadingAssetsFragmentTest {

    @Test
    fun `should show title name game`() {
        //given
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)

        //when
        scenario.moveToState(Lifecycle.State.RESUMED)

        //then
        onView(withId(R.id.title_name_game)).check(matches(withText(R.string.title_name_game)))
    }

    @Test
    fun `should loadingAssets visible`() {
        //given
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)
        var loadingAssetsBar: LoadingAssetsBar? = null
        scenario.onFragment { fragment ->
            loadingAssetsBar = fragment.view?.findViewById(R.id.loading_assets_bar)
        }

        //when
        scenario.moveToState(Lifecycle.State.RESUMED)

        //then
        assertNotNull(loadingAssetsBar)
        assertEquals(View.VISIBLE, loadingAssetsBar!!.visibility)
    }

    @Test
    fun `should use right layout`() {
        //given
        var expectedIdLayout = R.layout.fragment_download_assets
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)
        scenario.onFragment {
            fragment ->fragment.layoutI
        }
        //when
        scenario.moveToState(Lifecycle.State.STARTED)

        //then
        assertNotNull(loadingAssetsBar)
        assertEquals(expectedString, loadingAssetsBar!!.textStatusLine2)
    }

}