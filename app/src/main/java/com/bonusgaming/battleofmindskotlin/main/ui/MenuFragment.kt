package com.bonusgaming.battleofmindskotlin.main.ui

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
import com.bonusgaming.battleofmindskotlin.*
import com.bonusgaming.battleofmindskotlin.main.vm.MenuViewModel
import com.bonusgaming.battleofmindskotlin.tools.sendIntentForNextState
import com.squareup.picasso.Picasso
import java.io.File
import javax.inject.Inject


class MenuFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
      //  App.appComponent.getMenuComponent().inject(this)
        App.appComponent.getMenuComponent().inject(this)
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
            sendIntentForNextState(it)
        })

        menuViewModel.avatarChanged.observe(viewLifecycleOwner, Observer {
            picasso.load(File(it.imageFullPath)).into(avatarImageView)
            avatarTextView.text = it.avatarNickname
        })

    }
}