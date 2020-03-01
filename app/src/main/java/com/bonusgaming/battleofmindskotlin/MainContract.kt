package com.bonusgaming.battleofmindskotlin

import android.content.Context
import android.util.Log
import com.bonusgaming.battleofmindskotlin.db.Database
import javax.inject.Inject

interface MainContract {
    interface View {
        fun changeFragment(state: FragmentState)
    }

    abstract class ViewModel : androidx.lifecycle.ViewModel() {
        @Inject
        lateinit var model: MainModel

        init {
            Log.e("ViewModel", "contract ViewModel created")
            App.appComponent.inject(this)
        }

        abstract fun onViewCreated()
    }

    abstract class Model {
        @Inject
        lateinit var prefs: Prefs

        @Inject
        lateinit var appContext: Context

        @Inject
        lateinit var database: Database

        init {
            Log.e("Model", "contract model created")
            App.appComponent.inject(this)
        }
    }

}