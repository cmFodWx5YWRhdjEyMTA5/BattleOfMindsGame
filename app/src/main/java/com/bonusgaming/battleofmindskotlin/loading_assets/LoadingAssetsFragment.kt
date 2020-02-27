package com.bonusgaming.battleofmindskotlin.loading_assets


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.custom_views.LoadingAssetsBar

//фрагмент для отображения состояния загрузки и приветствия
class LoadingAssetsFragment : Fragment() {

    private lateinit var mainViewModel: LoadingAssetsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_assets, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProvider(this).get(LoadingAssetsViewModel::class.java)

        mainViewModel.onViewCreated()
        val progressBar = view.findViewById<LoadingAssetsBar>(R.id.loading_assets_bar)

        mainViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            progressBar.progress = it
        })

        mainViewModel.textStatusLine1LiveData.observe(viewLifecycleOwner, Observer {
            progressBar.textStatusLine1 = it
        })

        mainViewModel.loadSceneLiveData.observe(viewLifecycleOwner, Observer {
            Log.e("FrView", "on loadSceneLiveData")
            LocalBroadcastManager.getInstance(requireContext())
                .sendBroadcast(it)

        })

        mainViewModel.textStatusLine2LiveData.observe(viewLifecycleOwner, Observer {
            progressBar.textStatusLine2 = it
        })


    }


}