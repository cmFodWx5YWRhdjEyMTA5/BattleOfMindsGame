package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val bodyImageView = view.findViewById<ImageView>(R.id.image_shape)
        val faceImageView = view.findViewById<ImageView>(R.id.image_face)
        val avatar = Avatar(bodyImageView, faceImageView)

        creatingAvatarViewModel = ViewModelProvider(this).get(CreatingAvatarViewModel::class.java)



        creatingAvatarViewModel.initState.observe(viewLifecycleOwner, Observer {
            if (it) {
                creatingAvatarViewModel.fillAvatarRandom(avatar)
            }
        })

    }
}