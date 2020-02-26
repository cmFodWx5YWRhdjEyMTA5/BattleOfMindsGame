package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.app.Activity
import android.content.Intent
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse

class CreatingAvatarFragment : Fragment() {

    lateinit var creatingAvatarViewModel: CreatingAvatarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_creating_avatar, null)
    }

    companion object {
        private const val RC_SIGN_IN = 22
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bodyImageView = view.findViewById<ImageView>(R.id.image_body)
        val leftButton = view.findViewById<CardView>(R.id.arrow_left)
        val rightButton = view.findViewById<CardView>(R.id.arrow_right)

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

    private fun startFirebaseAuth() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                creatingAvatarViewModel.onLoginSuccess(response)

            } else {
                creatingAvatarViewModel.onLoginFailed(response)
            }
        }
    }

}