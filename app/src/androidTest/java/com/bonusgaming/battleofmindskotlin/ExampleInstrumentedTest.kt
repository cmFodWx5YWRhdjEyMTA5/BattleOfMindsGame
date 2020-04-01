package com.bonusgaming.battleofmindskotlin

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.bonusgaming.battleofmindskotlin.LoadingAssetsMatcher.Companion.withLoadingAssetsBarText
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class ExampleInstrumentedTest {

    private var mockWevServer = MockWebServer()

    @Before
    fun setUp() {
        mockWevServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWevServer.shutdown()
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.bonusgaming.battleofmindskotlin", appContext.packageName)
    }

    @Test
    fun shouldShowLogoText() {
        //given
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val bonusText = appContext.getString(R.string.bonus_text)
        val gamingText = appContext.getString(R.string.gaming_text)

        //when
        launchFragmentInContainer<HelloFragment>(Bundle.EMPTY, R.style.AppTheme)

        //then
        onView(withId(R.id.id_logo_bonus_text)).check((matches(ViewMatchers.withText(bonusText))))
        onView(withId(R.id.id_logo_gaming_text)).check((matches(ViewMatchers.withText(gamingText))))
    }

    @Test
    fun shouldShowAttemptConnectText() {
        //given
        mockWevServer.dispatcher = ResponseDispatcher()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val actionBadText = appContext.getString(R.string.desire_emotion_bad_connection_action)
        val statusBadText = appContext.getString(R.string.desire_emotion_bad_connection_status)

        //when
        launchFragmentInContainer<LoadingAssetsFragment>(Bundle.EMPTY, R.style.AppTheme)

        //then
        onView(withId(R.id.loading_assets_bar)).check((
                matches(withLoadingAssetsBarText(statusBadText, actionBadText))))
    }
}
