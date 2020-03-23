
package com.bonusgaming.battleofmindskotlin.di.component

import android.app.Application
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacade
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppProvider
import com.bonusgaming.battleofmindskotlin.di.module.FragmentModule
import dagger.BindsInstance
import dagger.Component
import java.security.Provider


//@Component(modules = [FragmentModule::class],dependencies = [AppProvider::class])
//interface FacadeComponent : AppFacade {

//    companion object{
//        fun get(app:Provider): FacadeComponent{
//            return DaggerFa

//        }

//    }
//}