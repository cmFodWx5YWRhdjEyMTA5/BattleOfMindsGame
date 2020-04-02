package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.os.Bundle
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.bonusgaming.battleofmindskotlin.ui.LoadingAssetsBar
import com.bonusgaming.battleofmindskotlin.features.loading.R
import com.bonusgaming.battleofmindskotlin.features.loading.RxRule
import com.bonusgaming.battleofmindskotlin.features.loading.TestApp
import com.bonusgaming.battleofmindskotlin.features.loading.shadows.LoadingAssetsBarShadow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow


@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, shadows = [LoadingAssetsBarShadow::class])
class LoadingAssetsFragmentTest {

    @Rule
    @JvmField
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxRule: RxRule = RxRule()

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
        scenario.moveToState(Lifecycle.State.CREATED)

        //then
        assertNotNull(loadingAssetsBar)
        assertEquals(View.VISIBLE, loadingAssetsBar!!.visibility)
    }

    @Test
    fun `should show download complete`() {
        //given
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)
        var expectedText: String? = null
        var loadingAssetsBar: LoadingAssetsBar? = null
        scenario.onFragment {
            expectedText = it.requireActivity().application.resources.getString(R.string.download_complete)
            loadingAssetsBar = it.requireActivity().findViewById<LoadingAssetsBar>(R.id.loading_assets_bar)
        }

        //when
        scenario.moveToState(Lifecycle.State.CREATED)

        //then
        assertNotNull(expectedText)
        assertNotNull(loadingAssetsBar)
        assertEquals(expectedText, loadingAssetsBar!!.textStatusLine2)
    }

    /**
     * DbStub и WebStub возвращают одинаковые списки с размером в 100, чтобы не лезть в интернет
     * и вызывать метод updateProgress в ViewModel.
     * `perProgress == 1` так как 100F/list.size
     */
    @Test
    fun `should call update progress method with perProgress=1`() {
        //given
        val scenario = launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)
        val expectedPerProgress = 1
        val expectedEndProgress = 100
        var loadingAssetsBarShadow: LoadingAssetsBarShadow? = null

        //when
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment {
            loadingAssetsBarShadow = Shadow.extract(it.requireActivity()
                    .findViewById<LoadingAssetsBar>(R.id.loading_assets_bar)) as LoadingAssetsBarShadow
        }

        //then
        assertNotNull(loadingAssetsBarShadow)
        assertEquals(expectedPerProgress, loadingAssetsBarShadow!!.perProgress)
        assertEquals(expectedEndProgress, loadingAssetsBarShadow!!.lastProgress)
    }

}