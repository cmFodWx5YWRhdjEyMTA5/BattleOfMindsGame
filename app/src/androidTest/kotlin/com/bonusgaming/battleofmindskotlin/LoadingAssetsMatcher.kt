package com.bonusgaming.battleofmindskotlin

import android.view.View
import com.bonusgaming.battleofmindskotlin.base_ui.LoadingAssetsBar
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.lang.Exception


class LoadingAssetsMatcher private constructor(private val line1: String, private val line2: String) : TypeSafeMatcher<View?>() {

    companion object {
        fun withLoadingAssetsBarText(line1: String, line2: String): Matcher<View?>? {
            return LoadingAssetsMatcher(line1, line2)
        }
    }

    override fun describeTo(description: Description) {
        description.appendText("same text with LoadingAssetsBar view")
    }

    override fun matchesSafely(item: View?): Boolean {
        return try {
            val loadingAssetsBar = item as LoadingAssetsBar
            loadingAssetsBar.textStatusLine1 == line1 && loadingAssetsBar.textStatusLine2 == line2
        } catch (e: Exception) {
            false
        }
    }
}