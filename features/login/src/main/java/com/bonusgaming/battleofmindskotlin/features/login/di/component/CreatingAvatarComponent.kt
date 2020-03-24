package com.bonusgaming.battleofmindskotlin.features.login.di.component

import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFragment
import dagger.Component

@PerFragment
@Component(dependencies = [LoginComponent::class])
interface CreatingAvatarComponent{
    companion object{
//        fun get(loginComponent:LoginComponent):CreatingAvatarComponent{
//          //  val crate
//        }
    }

}