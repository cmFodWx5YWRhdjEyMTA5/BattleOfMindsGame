package com.bonusgaming.battleofmindskotlin.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.tools.ActivityUtils
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {

    var menuViewModel: MenuViewModel = MenuViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("123", "MainFragment created")
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        Picasso.get()
            .load("https://pixabay.com/get/5ee8d2414d50b108f5d08460962f307f173adde34e50744c7d287cdc9044c2_1280.jpg")
            .into(view.findViewById<ImageView>(R.id.image_view_logo_main))



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }

    fun initViews(view: View) {
        val startButton: Button = view.findViewById(R.id.button_start)
        startButton.setOnClickListener { menuViewModel.onStartClick() }

        menuViewModel.getLiveFragmentState().observe(this, Observer {
            ActivityUtils.replaceFragment(activity!!.supportFragmentManager, LoadingFragment())
        })

    }
}