package com.bonusgaming.battleofmindskotlin.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.tools.ActivityUtils


class MainFragment : Fragment() {

    var menuViewModel: MenuViewModel = MenuViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("123", "MainFragment created")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }

    fun initViews(view: View) {
        val startButton: CardView = view.findViewById(R.id.button_start)
        startButton.setOnClickListener { menuViewModel.onStartClick() }

        menuViewModel.getLiveFragmentState().observe(viewLifecycleOwner, Observer {
            ActivityUtils.replaceFragment(activity!!.supportFragmentManager, LoadingFragment())
        })

    }
}