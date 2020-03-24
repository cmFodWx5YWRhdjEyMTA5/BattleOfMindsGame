package com.bonusgaming.battleofmindskotlin.features.login.logo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.features.login.R
import javax.inject.Inject

@PerFragment
class HelloFragment @Inject constructor() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("123", "Hello Fr created")
        return inflater.inflate(R.layout.fragment_hello, null)
    }
}