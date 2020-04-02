package com.bonusgaming.battleofmindskotlin.features.loading.di.module

import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideLoading() = LoadingAssetsFragment()

}