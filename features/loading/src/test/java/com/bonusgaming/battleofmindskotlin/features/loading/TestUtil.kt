package com.bonusgaming.battleofmindskotlin.features.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.bonusgaming.battleofmindskotlin.core.main.dto.UrlSticker
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mockito
import java.security.MessageDigest
import kotlin.random.Random

private const val CHARS = "abcdefghijklmnopqrstuvwxyz0123456789-_/"

/**
 * в методое `viewModelProvider.get(this@callOnClearedForTest::class.java)`
 * вызывается  `mViewModelStore.put(key, viewModel);` для ViewModelStore
 * и мы можем вызвать clear для ViewModelStore, чтобы получить onCleared()
 * в ViewModel
 */
fun ViewModel.callOnClearedForTest() {
    val viewModelStore = ViewModelStore()
    val viewModelProvider = ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = this@callOnClearedForTest as T
    })

    viewModelProvider.get(this@callOnClearedForTest::class.java)

    viewModelStore.clear()
}

fun <T> anyObject(): T {
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
    Mockito.any<T>()
    return uninitialized()
}

private fun ByteArray.toHexString(): String {
    return this.joinToString("") {
        String.format("%02x", it)
    }
}

private fun getRandomSize(): String {
    return Random.nextInt(1, 1000).toString()
}

private fun getRandomName(): String {
    val buffer = StringBuffer()
    for (i in 0..10) {
        buffer.append(CHARS[Random.nextInt(0, CHARS.length - 1)])
    }
    return buffer.toString()
}

private fun getRandomUrl(): String {
    val buffer = StringBuffer()
    buffer.append("https://someurl/")
    for (i in 0..10) {
        buffer.append(CHARS[Random.nextInt(0, CHARS.length - 1)])
    }
    return buffer.toString()
}

fun getRandomMD5(): String {
    val randomBytes = Random.nextBytes(1000)
    val digest = MessageDigest.getInstance("MD5").apply { update(randomBytes) }.digest()

    return digest.toHexString()
}

fun getRandomUrlSticker() =
        UrlSticker(getRandomUrl(),
                getRandomName(),
                getRandomSize(),
                getRandomMD5()
        )


fun getRandomUrlStickerList(size: Int): List<UrlSticker> {
    return mutableListOf<UrlSticker>().apply {
        for (i in 0..size)
            add(getRandomUrlSticker())
    }
}

class RxRule : TestRule {
    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }

            }
        }
    }
}


