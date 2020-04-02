package com.bonusgaming.battleofmindskotlin.features.loading.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.dbapi.DbApiProvider
import com.bonusgaming.battleofmindskotlin.ui.LoadingAssetsBar
import com.bonusgaming.battleofmindskotlin.ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.ui.nextState
import com.bonusgaming.battleofmindskotlin.webapi.WebApiProvider
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.loading.R
import com.bonusgaming.battleofmindskotlin.features.loading.di.component.LoadingAssetsComponent
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

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(LoadingAssetsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setViewModel()
        mainViewModel.onViewCreated()
        setObservers(view)
    }

    private fun setObservers(createdView: View) {
        val progressBar = createdView.findViewById<LoadingAssetsBar>(R.id.loading_assets_bar)

        mainViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            println("fr live $it")
            progressBar.setProgress(it)
        })

        mainViewModel.textStatusLine1LiveData.observe(viewLifecycleOwner, Observer {
            println("fr live 1 $it")
            progressBar.textStatusLine1 = it
        })

        mainViewModel.loadSceneLiveData.observe(viewLifecycleOwner, Observer {
            nextState(it)
        })

        mainViewModel.textStatusLine2LiveData.observe(viewLifecycleOwner, Observer {
            println("fr live 2 $it")
            progressBar.textStatusLine2 = it
        })
    }
}
