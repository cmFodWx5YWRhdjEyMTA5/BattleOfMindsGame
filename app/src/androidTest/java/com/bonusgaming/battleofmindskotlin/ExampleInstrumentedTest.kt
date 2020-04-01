package com.bonusgaming.battleofmindskotlin


import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.MainActivityView
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {

    private var mockWevServer = MockWebServer()

    val activiyRule = ActivityTestRule<MainActivityView>(MainActivityView::class.java)

    @Before
    fun setUp() {
        mockWevServer.start(8080)
        mockWevServer.dispatcher = ResponseDispatcher()
    }

    @After
    fun tearDown() {
        mockWevServer.shutdown()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.bonusgaming.battleofmindskotlin", appContext.packageName)
    }

    @Test
    fun shouldShowLogoText() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

//        activiyRule.launchActivity(null)
        val scenario = launchFragmentInContainer<HelloFragment>(Bundle.EMPTY, R.style.AppTheme)



        val bonusText = appContext.getString(R.string.bonus_text)
        val gamingText = appContext.getString(R.string.gaming_text)

        println("1 $bonusText")
        println("2 $gamingText")

        Thread.sleep(10000)

        println("3 ${onView(withId(R.id.id_logo_bonus_text))}")
        isRoot()

        onView(withId(R.id.id_logo_bonus_text)).check(matches(ViewMatchers.withText(bonusText)))
        onView(withId(R.id.id_logo_gaming_text)).check(matches(ViewMatchers.withText(gamingText)))

        println("1 $bonusText")
        println("2 $gamingText")

        Thread.sleep(10000)

    }
}
