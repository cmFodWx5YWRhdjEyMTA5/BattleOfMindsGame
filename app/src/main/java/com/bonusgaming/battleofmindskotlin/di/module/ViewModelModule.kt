package com.bonusgaming.battleofmindskotlin.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarViewModel
import com.bonusgaming.battleofmindskotlin.di.scope.PerFragment
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule(val viewModelStoreOwner: ViewModelStoreOwner) {

    @Provides
    @PerFragment
    fun getCreatingAvatarViewModel(): CreatingAvatarViewModel {
        return ViewModelProvider(viewModelStoreOwner).get(CreatingAvatarViewModel::class.java)
    }
}