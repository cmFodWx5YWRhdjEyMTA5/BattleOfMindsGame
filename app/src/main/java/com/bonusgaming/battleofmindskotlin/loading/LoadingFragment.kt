package com.bonusgaming.battleofmindskotlin.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.custom_views.CustomLoadingValueAnimator

class LoadingFragment : Fragment() {
    lateinit var customLoading: CustomLoadingValueAnimator

    val loadingModelView = LoadingModelView()
    lateinit var enemyPhoto: View
    lateinit var enemyText: View

    init {
        loadingModelView.liveDataVisibleEnemy.observe(this, Observer {
            enemyPhoto.visibility = it
            enemyText.visibility = it
        })
        loadingModelView.liveDataVisibleLoading.observe(this, Observer {
            if (!it) {
                customLoading.startStopping(object : CustomLoadingValueAnimator.LoadingOnStop {
                    override fun onStop() {
                        loadingModelView.onStoppedLoading()
                    }
                })
            }
        })
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
        loadingModelView.onCreated()
    }
}