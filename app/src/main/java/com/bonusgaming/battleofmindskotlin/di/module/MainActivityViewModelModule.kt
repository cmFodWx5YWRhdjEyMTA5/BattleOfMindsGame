package com.bonusgaming.battleofmindskotlin.di.module

import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.MainViewModel
import com.bonusgaming.battleofmindskotlin.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainActivityViewModelModule {

    //не указываем scope, чтобы при пересоздании MainActivity пересоздавать MainViewModel
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun mainViewModelViewModel(mainViewModel: MainViewModel): ViewModel
}