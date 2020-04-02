package com.bonusgaming.battleofmindskotlin.ui.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.ui.presentation.MainViewModel
import com.bonusgaming.battleofmindskotlin.core.main.di.ViewModelKey
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFacade
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainViewModelModule {

    @PerFacade
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun mainViewModel(viewModel: MainViewModel): ViewModel

}
