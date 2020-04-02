package com.bonusgaming.battleofmindskotlin.core.main


import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Singleton

@Reusable
class ActivityUtils @Inject constructor() {

    public fun replaceFragment(container: Int, fragmentManager: FragmentManager, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(container, fragment)
        transaction.commit()
    }
}
