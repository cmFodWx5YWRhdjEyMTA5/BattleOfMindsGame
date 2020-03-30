package com.bonusgaming.battleofmindskotlin.features.loading.presentation


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApiProvider
import com.bonusgaming.battleofmindskotlin.base_ui.LoadingAssetsBar
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_ui.sendIntentForNextState
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApiProvider
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.loading.R
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.LoadingAssetsComponent
import com.bonusgaming.battleofmindskotlin.features.logo.di.module.LoadingViewModelFactory
import javax.inject.Inject


class LoadingAssetsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: LoadingViewModelFactory

    private lateinit var mainViewModel: LoadingAssetsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val appFacade = (requireActivity().application as AppFacadeProvider).provideAppFacade()
        val web = (requireActivity().application as WebApiProvider).provideWebApi()
        val db = (requireActivity().application as DbApiProvider).provideDbApi()
        LoadingAssetsComponent.get(
                appFacade,
                UiComponent.get(appFacade),
                web, db).inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_assets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("9789", "-----------------LoadingAssets onViewCreated $viewModelFactory")
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(LoadingAssetsViewModel::class.java)

        mainViewModel.onViewCreated()
        val progressBar = view.findViewById<LoadingAssetsBar>(R.id.loading_assets_bar)

        mainViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            progressBar.progress = it
        })

        mainViewModel.textStatusLine1LiveData.observe(viewLifecycleOwner, Observer {
            progressBar.textStatusLine1 = it
        })

        mainViewModel.loadSceneLiveData.observe(viewLifecycleOwner, Observer {
            sendIntentForNextState(it)
        })

        mainViewModel.textStatusLine2LiveData.observe(viewLifecycleOwner, Observer {
            progressBar.textStatusLine2 = it
        })
    }
}