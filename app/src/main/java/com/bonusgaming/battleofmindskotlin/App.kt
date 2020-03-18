package com.bonusgaming.battleofmindskotlin

import android.util.Log
import com.bonusgaming.battleofmindskotlin.di.component.AppComponent
import com.bonusgaming.battleofmindskotlin.di.component.DaggerAppComponent
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class App : DaggerApplication() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("poryadok", "oncreate")
        //  FirebaseApp.initializeApp(this)

    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }
}
