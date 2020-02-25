package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.R

class CreatingAvatarFragment : Fragment() {

    lateinit var creatingAvatarViewModel: CreatingAvatarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_creating_avatar, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bodyImageView = view.findViewById<ImageView>(R.id.image_body)
        val leftButton = view.findViewById<CardView>(R.id.arrow_left)
        val rightButton = view.findViewById<CardView>(R.id.arrow_right)
        leftButton.isClickable = true
        rightButton.isClickable = true

        val avatar = Avatar(bodyImageView)

        creatingAvatarViewModel = ViewModelProvider(this).get(CreatingAvatarViewModel::class.java)

        leftButton.setOnClickListener {
            creatingAvatarViewModel.onLeftButton()
        }

        rightButton.setOnClickListener {
            if (::creatingAvatarViewModel.isInitialized)
                creatingAvatarViewModel.onRightButton()
        }


        creatingAvatarViewModel.initState.observe(viewLifecycleOwner, Observer {
            if (it) {
                creatingAvatarViewModel.fillAvatarRandom(avatar)
            }
        })

    }
}