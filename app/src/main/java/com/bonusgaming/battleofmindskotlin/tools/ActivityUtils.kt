package com.bonusgaming.battleofmindskotlin.tools

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bonusgaming.battleofmindskotlin.R

object ActivityUtils {
    @JvmStatic
    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}