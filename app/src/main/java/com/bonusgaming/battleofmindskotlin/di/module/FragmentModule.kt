package com.bonusgaming.battleofmindskotlin.di.module

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.base_ui.di.FragmentStateKey
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.logo.presentation.HelloFragment
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