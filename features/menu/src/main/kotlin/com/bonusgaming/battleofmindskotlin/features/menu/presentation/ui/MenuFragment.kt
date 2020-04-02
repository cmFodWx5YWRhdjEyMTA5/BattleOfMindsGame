package com.bonusgaming.battleofmindskotlin.features.menu.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApiProvider
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_ui.nextState
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApiProvider
import com.bonusgaming.battleofmindskotlin.core.main.contract.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.menu.R
import com.bonusgaming.battleofmindskotlin.features.menu.di.component.MenuComponent
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.vm.MenuViewModel
import com.bonusgaming.battleofmindskotlin.features.menu.presentation.vm.MenuViewModelFactory
import com.squareup.picasso.Picasso
import java.io.File
import javax.inject.Inject


class MenuFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: MenuViewModelFactory

    @Inject
    lateinit var picasso: Picasso

    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        menuViewModel = ViewModelProvider(this, viewModelFactory)[MenuViewModel::class.java]
        initViews(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appFacade = (requireActivity().application as AppFacadeProvider).provideAppFacade()
        val ui = UiComponent.get(appFacade)
        val web = (requireActivity().application as WebApiProvider).provideWebApi()
        val db = (requireActivity().application as DbApiProvider).provideDbApi()
        MenuComponent.get(appFacade, ui, web, db).inject(this)
        super.onCreate(savedInstanceState)
    }

    fun initViews(view: View) {
        val startButton: CardView = view.findViewById(R.id.button_start)
        val statisticsButton: CardView = view.findViewById(R.id.button_statistics)

        val avatarImageView: ImageView = view.findViewById(R.id.menu_avatar_image)
        val avatarTextView: TextView = view.findViewById(R.id.menu_avatar_name)

        startButton.setOnClickListener { menuViewModel.onStartClick() }

        statisticsButton.setOnClickListener { menuViewModel.onStatisticsClick() }

        menuViewModel.liveFragmentState.observe(viewLifecycleOwner, Observer {
            nextState(it)
        })

        menuViewModel.avatarChanged.observe(viewLifecycleOwner, Observer {
            picasso.load(File(it.imageFullPath)).into(avatarImageView)
            avatarTextView.text = it.avatarNickname
        })

    }
}