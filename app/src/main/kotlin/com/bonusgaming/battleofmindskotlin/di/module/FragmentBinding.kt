package com.bonusgaming.battleofmindskotlin.di.module

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.base_ui.di.FragmentStateKey
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.ui.MenuFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentBinding {

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.LOGO)
    abstract fun bindLogo(fragment: HelloFragment): Fragment

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.DOWNLOAD_ASSETS)
    abstract fun bindLoading(fragment: LoadingAssetsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.AVATAR)
    abstract fun bindCreatingAvatar(fragment: CreatingAvatarFragment): Fragment

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.MENU)
    abstract fun bindMenu(fragment: MenuFragment): Fragment
}