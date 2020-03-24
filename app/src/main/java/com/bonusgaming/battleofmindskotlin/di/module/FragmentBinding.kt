package com.bonusgaming.battleofmindskotlin.di.module

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.base_ui.di.FragmentStateKey
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentBinding {

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.LOGO)
    abstract fun bindLogo(fragment: HelloFragment): Fragment
}