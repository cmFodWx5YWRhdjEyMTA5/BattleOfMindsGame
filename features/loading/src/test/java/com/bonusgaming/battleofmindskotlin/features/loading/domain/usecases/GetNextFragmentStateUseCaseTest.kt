package com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases

import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class GetNextFragmentStateUseCaseTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    private lateinit var getNextFragmentStateUseCase: GetNextFragmentStateUseCase

    @Before
    fun setUp() {
        getNextFragmentStateUseCase = GetNextFragmentStateUseCase(loadingAssetsRepository)
    }

    @Test
    fun `should return menu state`() {
        //given
        val expected = FragmentState.MENU

        //when
        Mockito.`when`(loadingAssetsRepository.isAvatarCreated())
                .thenReturn(true)
        val result = getNextFragmentStateUseCase.execute()

        //then
        assertEquals(expected, result)
    }

    @Test
    fun `should return avatar state`() {
        //given
        val expected = FragmentState.AVATAR

        //when
        Mockito.`when`(loadingAssetsRepository.isAvatarCreated())
                .thenReturn(false)
        val result = getNextFragmentStateUseCase.execute()

        //then
        assertEquals(expected, result)
    }
}