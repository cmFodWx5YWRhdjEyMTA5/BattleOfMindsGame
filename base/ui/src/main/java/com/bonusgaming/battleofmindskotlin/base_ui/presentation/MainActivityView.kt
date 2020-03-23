package com.bonusgaming.battleofmindskotlin.base_ui.presentation


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.base_ui.R
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_ui.presentation.mediator.StateMediator
import com.bonusgaming.battleofmindskotlin.core.main.ActivityUtils
import com.bonusgaming.battleofmindskotlin.core.main.FragmentState
import com.bonusgaming.battleofmindskotlin.core.main.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacadeProvider
import javax.inject.Inject


class MainActivityView : AppCompatActivity(), StateMediator {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var activityUtils: ActivityUtils

    @Inject
    lateinit var mapFragment: Map<FragmentState, @JvmSuppressWildcards Fragment>

    lateinit var mainViewModel: MainViewModel


    override fun onDestroy() {
        super.onDestroy()
        Log.e("9789", "-----------------MainActivityView onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        UiComponent.get((application as AppFacadeProvider).provideAppFacade()).inject(this)
        Log.e("9789", "-----------------MainActivityView onCreate $viewModelFactory")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        Log.e("9789", "-----------------mainViewModel ${mainViewModel}")

        mainViewModel.getData().observe(this, Observer {
            Log.e("9789", "mutable data $it")
            nextState(it)
        })

        mainViewModel.onViewCreated()
    }


//    fun changeFragment(state: FragmentState) {
//        val fr: Fragment = when (state) {
//            FragmentState.LOGO -> HelloFragment()
//            FragmentState.DOWNLOAD_ASSETS -> LoadingAssetsFragment()
//            FragmentState.AVATAR -> CreatingAvatarFragment()
//            FragmentState.MAIN -> MenuFragment()
//            FragmentState.LOADING -> LoadingFragment()
//            FragmentState.GAME -> GameFragment()
//            FragmentState.RESULT -> GameFragment()//not implemented
//            FragmentState.SETTINGS -> GameFragment()//not implemented
//            FragmentState.STATISTICS -> LoadingFragment()//not implemented
//        }
//
//        //  ActivityUtils.replaceFragment(supportFragmentManager, fr)
//    }

    override fun nextState(state: FragmentState) {
        val fragment = mapFragment[state]
        fragment?.let {
            activityUtils.replaceFragment(R.id.fragment_container, supportFragmentManager, it)
        }
    }


}
