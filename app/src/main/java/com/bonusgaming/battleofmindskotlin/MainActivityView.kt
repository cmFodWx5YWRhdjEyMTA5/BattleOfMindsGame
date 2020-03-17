package com.bonusgaming.battleofmindskotlin


import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.creating_avatar.CreatingAvatarFragment
import com.bonusgaming.battleofmindskotlin.game.GameFragment
import com.bonusgaming.battleofmindskotlin.loading_assets.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.loading_game.LoadingFragment
import com.bonusgaming.battleofmindskotlin.logo.HelloFragment
import com.bonusgaming.battleofmindskotlin.main.ui.MenuFragment
import com.bonusgaming.battleofmindskotlin.tools.ActivityUtils


class MainActivityView : AppCompatActivity(), MainContract.View {

    private lateinit var mainViewModel: MainViewModel

    override fun changeFragment(state: FragmentState) {
        Log.e("123231", "MainActivityView changeFragment " + state)
        val fr: Fragment = when (state) {
            FragmentState.LOGO -> HelloFragment()
            FragmentState.DOWNLOAD_ASSETS -> LoadingAssetsFragment()
            FragmentState.AVATAR -> CreatingAvatarFragment()
            FragmentState.MAIN -> MenuFragment()
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
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getData().observe(this, Observer {
            changeFragment(it)
        })
    }
}
