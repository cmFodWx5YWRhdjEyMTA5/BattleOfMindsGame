package com.bonusgaming.battleofmindskotlin.features.loading.presentation

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import com.bonusgaming.battleofmindskotlin.features.loading.*
import com.bonusgaming.battleofmindskotlin.features.loading.anyObject
import com.bonusgaming.battleofmindskotlin.features.loading.data.LoadingAssetsRepository
import com.bonusgaming.battleofmindskotlin.features.loading.domain.usecases.*
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.lang.Exception

class LoadingAssetsViewModelTest {

    @Rule
    @JvmField
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxRule: RxRule = RxRule()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var loadingAssetsRepository: LoadingAssetsRepository

    @Mock
    lateinit var downloadUrlsUseCase: DownloadUrlsUseCase

    @Mock
    lateinit var downloadStickerUseCase: DownloadStickerUseCase

    @Mock
    lateinit var getNextFragmentStateUseCase: GetNextFragmentStateUseCase

    @Mock
    lateinit var saveStickers: SaveStickerUseCase

    @Mock
    lateinit var getSavedStickersListUseCase: GetSavedStickersListUseCase

    @Mock
    lateinit var resources: Resources

    private lateinit var loadingAssetsViewModel: LoadingAssetsViewModel

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    private val excpectedDownloadStart="start"
    private val excpectedDownloadEnd="end"
    private val excpectedDownload="download"

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {

        Dispatchers.setMain(mainThreadSurrogate)
        loadingAssetsViewModel = LoadingAssetsViewModel(downloadUrlsUseCase,
                downloadStickerUseCase,
                getNextFragmentStateUseCase,
                saveStickers,
                getSavedStickersListUseCase,
                resources)
        `when`(resources.getString(R.string.download)).thenReturn(excpectedDownload)
        `when`(resources.getString(R.string.download_start)).thenReturn(excpectedDownloadStart)
        `when`(resources.getString(R.string.download_complete)).thenReturn(excpectedDownloadEnd)

    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `should call download func after onViewCreated`() {
        //given
        val disposable = Single.just {
            val mutableList = listOf<List<UrlSticker>>()
            mutableList
        }.subscribe()

        //when
        `when`(downloadUrlsUseCase.execute(anyObject(), anyObject())).then {
            disposable
        }
        loadingAssetsViewModel.onViewCreated()

        //then
        verify(downloadUrlsUseCase).execute(anyObject(), anyObject())
    }

    @Test
    fun `should all Disposable closed`() {
        //given
        val disposable = Single.just {
            val mutableList = listOf<List<UrlSticker>>()
            mutableList
        }.subscribe()

        //when
        `when`(downloadUrlsUseCase.execute(anyObject(), anyObject())).then {
            disposable
        }
        loadingAssetsViewModel.onViewCreated()
        loadingAssetsViewModel.callOnClearedForTest()

        //then
        val result = loadingAssetsViewModel.isAllDisposablesClosed
        assertTrue("isAllDisposablesClosed should be true", result)
    }

    @Test
    fun `should changed live data bad connection`() {
        //given
        val expectedString1 = "some exception line 1"
        val expectedString2 = "some exception line 2"
        val downloadUrlsUseCaseReal = DownloadUrlsUseCase(loadingAssetsRepository)
        val loadingAssetsViewModelReal = LoadingAssetsViewModel(
                downloadUrlsUseCaseReal,
                downloadStickerUseCase,
                getNextFragmentStateUseCase,
                saveStickers,
                getSavedStickersListUseCase, resources)

        //when
        `when`(loadingAssetsRepository.getStickerUrls())
                .thenReturn(Single.error(Exception("some bad connection")))

        @Suppress("UNCHECKED_CAST")
        val mockObserver1: Observer<String> = mock(Observer::class.java) as (Observer<String>)

        @Suppress("UNCHECKED_CAST")
        val mockObserver2: Observer<String> = mock(Observer::class.java) as (Observer<String>)

        `when`(resources.getString(R.string.desire_emotion_bad_connection_status)).thenReturn(expectedString1)
        `when`(resources.getString(R.string.desire_emotion_bad_connection_action)).thenReturn(expectedString2)

        downloadUrlsUseCaseReal.isRetryWhenEnabled = false

        loadingAssetsViewModelReal.textStatusLine1LiveData.observeForever(mockObserver1)
        loadingAssetsViewModelReal.textStatusLine2LiveData.observeForever(mockObserver2)
        loadingAssetsViewModelReal.onViewCreated()

        //then
        verify(mockObserver1).onChanged(expectedString1)
        verify(mockObserver2).onChanged(expectedString2)
    }
}