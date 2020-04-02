package com.bonusgaming.battleofmindskotlin.di.module

import com.bonusgaming.battleofmindskotlin.features.avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.ui.MenuFragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideLogo() = HelloFragment()

    @Provides
    fun provideCreatingAvatar() = CreatingAvatarFragment()

    @Provides
    fun provideMenu() = MenuFragment()


    @Provides
    fun provideLoading() = LoadingAssetsFragment()
}
