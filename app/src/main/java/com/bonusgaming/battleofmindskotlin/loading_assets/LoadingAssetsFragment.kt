package com.bonusgaming.battleofmindskotlin.loading_assets


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.custom_views.LoadingAssetsBar

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
        mainViewModel = ViewModelProviders.of(this).get(LoadingAssetsViewModel::class.java)

        mainViewModel.onViewCreated()
        val progressBar = view.findViewById<LoadingAssetsBar>(R.id.loading_assets_bar)

        mainViewModel.progressLiveData.observe(viewLifecycleOwner, Observer {
            progressBar.progress = it

        })

        mainViewModel.textStatusLiveData.observe(viewLifecycleOwner, Observer {
            progressBar.textStatus = it
        })
    }
}