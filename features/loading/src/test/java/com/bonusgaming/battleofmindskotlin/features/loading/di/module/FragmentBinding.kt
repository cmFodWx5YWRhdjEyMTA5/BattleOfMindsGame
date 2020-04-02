package com.bonusgaming.battleofmindskotlin.features.loading.di.module

import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.ui.di.FragmentStateKey
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentBinding {
    @Binds
    @IntoMap
    @FragmentStateKey(FragmentState.DOWNLOAD_ASSETS)
    abstract fun bindLoading(fragment: LoadingAssetsFragment): Fragment
}