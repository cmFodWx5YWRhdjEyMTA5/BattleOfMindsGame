package com.bonusgaming.battleofmindskotlin.features.logo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_ui.sendIntentForNextState
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.logo.R
import com.bonusgaming.battleofmindskotlin.features.logo.di.components.LogoComponent
import javax.inject.Inject

class HelloFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: LogoViewModelFactory

    lateinit var viewModel: HelloViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val appFacade = (requireActivity().application as AppFacadeProvider).provideAppFacade()
        LogoComponent.get(UiComponent.get(appFacade)).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("123", "Hello Fr created")
        viewModel = ViewModelProvider(this, viewModelFactory)[HelloViewModel::class.java]

        viewModel.stateLiveData.observe(viewLifecycleOwner,
                Observer {
                    sendIntentForNextState(it)
                })

        return inflater.inflate(R.layout.fragment_hello, null)
    }
}