package com.bonusgaming.battleofmindskotlin.di.module

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.base_ui.di.FragmentStateKey
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.login.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.features.login.loading_assets.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.login.logo.HelloFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentModule {

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.LOGO)
    fun provideLogo(fragment: HelloFragment): Fragment

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.DOWNLOAD_ASSETS)
    fun provideDownloadingAssets(fragment: LoadingAssetsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.AVATAR)
    fun provideAvatar(fragment: CreatingAvatarFragment): Fragment

}