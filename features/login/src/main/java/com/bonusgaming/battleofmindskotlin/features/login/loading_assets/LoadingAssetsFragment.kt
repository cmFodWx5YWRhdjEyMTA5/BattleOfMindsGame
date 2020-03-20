package com.bonusgaming.battleofmindskotlin.login.loading_assets


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.custom_views.LoadingAssetsBar
import com.bonusgaming.battleofmindskotlin.tools.sendIntentForNextState
import javax.inject.Inject

//фрагмент для отображения состояния загрузки
class LoadingAssetsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mainViewModel: LoadingAssetsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        //App.appComponent.getLoadingAssetsComponent().inject(this)
        App.appComponent.getLoadingAssetsComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_assets, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("9789","-----------------LoadingAssets onViewCreated $viewModelFactory")
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