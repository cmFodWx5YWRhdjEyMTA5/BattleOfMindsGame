package com.bonusgaming.battleofmindskotlin


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bonusgaming.battleofmindskotlin.game.GameFragment
import com.bonusgaming.battleofmindskotlin.loading_assets.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.logo.HelloFragment
import com.bonusgaming.battleofmindskotlin.main.MainFragment
import com.bonusgaming.battleofmindskotlin.tools.ActivityUtils


class MainActivityView : AppCompatActivity(), MainContract.View {

    private lateinit var mainViewModel: MainViewModel

    override fun changeFragment(state: FragmentState) {
        Log.e("123231", "MainActivityView changeFragment " + state)
        val fr: Fragment = when (state) {
            FragmentState.LOGO -> HelloFragment()
            FragmentState.DOWNLOAD_ASSETS -> LoadingAssetsFragment()
            FragmentState.AVATAR -> HelloFragment()//not implemented
            FragmentState.MAIN -> MainFragment()
            FragmentState.LOADING -> LoadingFragment()
            FragmentState.GAME -> GameFragment()
            FragmentState.RESULT -> GameFragment()//not implemented
            FragmentState.SETTINGS -> GameFragment()//not implemented
            FragmentState.STATISTICS -> GameFragment()//not implemented
        }

        ActivityUtils.replaceFragment(supportFragmentManager, fr)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        attachToViewModel()
        mainViewModel.onViewCreated()
    }

    private fun attachToViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getData().observe(this, Observer {
            changeFragment(it)
        })
    }
}