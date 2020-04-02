package com.bonusgaming.battleofmindskotlin.base_ui.di

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class FragmentStateKey(val value: FragmentState)