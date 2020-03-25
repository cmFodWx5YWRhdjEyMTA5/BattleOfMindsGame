package com.bonusgaming.battleofmindskotlin.di.module

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.base_ui.di.FragmentStateKey
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.creating_avatar.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.features.loading.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.ui.MenuFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class FragmentModule {

//    @Binds
//    @IntoMap
//    @FragmentStateKey(FragmentState.LOGO)
//    fun provideLogo(fragment: HelloFragment): Fragment

    @Provides
    fun provideLogo() = HelloFragment()

    @Provides
    fun provideCreatingAvatar() = CreatingAvatarFragment()

    @Provides
    fun provideMenu() = MenuFragment()


    @Provides
    fun provideLoading() = LoadingAssetsFragment()
//
//    @Binds
//    @IntoMap
//    @FragmentStateKey(FragmentState.LOGO)
//    abstract fun bindLogo(fragment: HelloFragment): Fragment

//    @Binds
//    @IntoMap
//    @FragmentStateKey(FragmentState.DOWNLOAD_ASSETS)
//    fun provideDownloadingAssets(fragment: LoadingAssetsFragment): Fragment
//
//    @Binds
//    @IntoMap
//    @FragmentStateKey(FragmentState.AVATAR)
//    fun provideAvatar(fragment: CreatingAvatarFragment): Fragment

}