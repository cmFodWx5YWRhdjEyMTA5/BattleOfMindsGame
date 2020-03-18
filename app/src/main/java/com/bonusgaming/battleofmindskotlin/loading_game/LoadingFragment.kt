package com.bonusgaming.battleofmindskotlin.loading_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.custom_views.CustomLoadingValueAnimator
import javax.inject.Inject

class LoadingFragment : Fragment() {
    lateinit var customLoading: CustomLoadingValueAnimator

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var loadingViewModel: LoadingViewModel
    lateinit var enemyPhoto: View
    lateinit var enemyText: View


    private fun initViewModel() {
        loadingViewModel = ViewModelProvider(this, viewModelFactory)[LoadingViewModel::class.java]

        loadingViewModel.liveDataVisibleEnemy.observe(viewLifecycleOwner, Observer {
            enemyPhoto.visibility = it
            enemyText.visibility = it
        })
        loadingViewModel.liveDataVisibleLoading.observe(viewLifecycleOwner, Observer {
            if (!it) {
                customLoading.startStopping(object : CustomLoadingValueAnimator.LoadingOnStop {
                    override fun onStop() {
                        loadingViewModel.onStoppedLoading()
                    }
                })
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       // App.appComponent.getLoadingComponent().inject(this)
        App.appComponent.getLoadingComponent().inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_loading, container, false)
        customLoading = view.findViewById(R.id.custom_loading)
        enemyPhoto = view.findViewById(R.id.image_enemy)
        enemyText = view.findViewById(R.id.name_enemy)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        loadingViewModel.onCreated()
    }
}