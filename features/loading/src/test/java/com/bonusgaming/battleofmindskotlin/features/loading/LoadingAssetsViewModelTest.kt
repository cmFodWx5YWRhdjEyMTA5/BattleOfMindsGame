package com.bonusgaming.battleofmindskotlin.features.loading

import com.bonusgaming.battleofmindskotlin.features.loading.domain.use_cases.DownloadUrlsUseCase
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadingAssetsViewModelTest {

    @Mock
    lateinit var downloadUrlsUseCase: DownloadUrlsUseCase

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onViewCreated() {

    }

    @Test
    fun onCleared() {
    }
}