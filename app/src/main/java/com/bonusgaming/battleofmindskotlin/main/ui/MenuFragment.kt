package com.bonusgaming.battleofmindskotlin.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.App
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarViewModel
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.main.vm.MenuViewModel
import com.bonusgaming.battleofmindskotlin.tools.ActivityUtils
import javax.inject.Inject


class MenuFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e("123", "MainFragment created")
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.getMenuComponent().inject(this)
        super.onCreate(savedInstanceState)

        menuViewModel = ViewModelProvider(this, viewModelFactory)[MenuViewModel::class.java]
    }

    fun initViews(view: View) {
        val startButton: CardView = view.findViewById(R.id.button_start)
        startButton.setOnClickListener { menuViewModel.onStartClick() }

        menuViewModel.getLiveFragmentState().observe(viewLifecycleOwner, Observer {
            ActivityUtils.replaceFragment(activity!!.supportFragmentManager, LoadingFragment())
        })

    }
}