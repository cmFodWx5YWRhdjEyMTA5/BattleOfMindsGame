package com.bonusgaming.battleofmindskotlin.sign_in

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.MainModel
import javax.inject.Inject

class SignInViewModel : ViewModel() {

    companion object {
        const val STATE_SIGN_IN = 0
        const val STATE_HELLO = 1
    }

    val stateLiveData = MutableLiveData<Int>()

    @Inject
    lateinit var mainModel: MainModel

    init {
        App.appComponent.inject(this)
        if (mainModel.isAvatarCreated()) stateLiveData.value = STATE_HELLO
        else stateLiveData.value = STATE_SIGN_IN
    }

    fun onSignInClick(){

    }

    fun onPlayAnomymouslyClick(){

    }

}