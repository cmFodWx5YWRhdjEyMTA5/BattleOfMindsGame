package com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases

import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.getRandomMD5
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class GetSavedStickersListTest {

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    private lateinit var getSavedStickersList: GetSavedStickersListUseCase

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        getSavedStickersList = GetSavedStickersListUseCase(loadingAssetsRepository)
    }

    @Test
    fun `should return expected list`() {
        //given
        val expectedList = mutableListOf<String>()
        for (i in 0..100) {
            expectedList.add(getRandomMD5())
        }

        //when
        `when`(loadingAssetsRepository.getHashStickersList()).thenReturn(expectedList)
        val result = getSavedStickersList.execute()

        //then
        assertEquals(expectedList, result)
    }
}