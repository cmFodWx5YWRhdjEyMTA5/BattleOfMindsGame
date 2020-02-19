package com.bonusgaming.battleofmindskotlin.loading_assets


class AssetsErrorHandler {
    var currentError = 0

    companion object {
        const val MAX_ATTEMPTS = 5
    }

    fun onError() {
        currentError++
        if (currentError > MAX_ATTEMPTS){

        }

    }

}