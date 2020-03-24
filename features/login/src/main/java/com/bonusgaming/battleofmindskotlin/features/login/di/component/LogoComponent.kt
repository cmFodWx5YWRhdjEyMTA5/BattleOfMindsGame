package com.bonusgaming.battleofmindskotlin.features.login.di.component

import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFragment
import dagger.Component

@PerFragment
@Component(dependencies = [LoginComponent::class])
interface LogoComponent {
}