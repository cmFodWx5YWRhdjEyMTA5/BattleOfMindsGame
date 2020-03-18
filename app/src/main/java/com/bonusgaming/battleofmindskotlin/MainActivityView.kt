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
import javax.inject.Inject


class MainActivityView : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainViewModel: MainViewModel

    override fun onDestroy() {
        super.onDestroy()
        Log.e("9789","-----------------MainActivityView onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        App.appComponent.inject(this)
        Log.e("9789","-----------------MainActivityView onCreate $viewModelFactory")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        Log.e("9789","-----------------mainViewModel ${mainViewModel}")

        mainViewModel.getData().observe(this, Observer {
            Log.e("9789","mutable data $it")
            changeFragment(it)
        })

        mainViewModel.onViewCreated()
    }


    fun changeFragment(state: FragmentState) {
        val fr: Fragment = when (state) {
            FragmentState.LOGO -> HelloFragment()
            FragmentState.DOWNLOAD_ASSETS -> LoadingAssetsFragment()
            FragmentState.AVATAR -> CreatingAvatarFragment()
            FragmentState.MAIN -> MenuFragment()
            FragmentState.LOADING -> LoadingFragment()
            FragmentState.GAME -> GameFragment()
            FragmentState.RESULT -> GameFragment()//not implemented
            FragmentState.SETTINGS -> GameFragment()//not implemented
            FragmentState.STATISTICS -> LoadingFragment()//not implemented
        }

        Log.e("9789"," ch fr $state $supportFragmentManager")
        ActivityUtils.replaceFragment(supportFragmentManager, fr)
    }


}
