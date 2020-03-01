package com.bonusgaming.battleofmindskotlin.creating_avatar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bonusgaming.battleofmindskotlin.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


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
        val playButton = view.findViewById<CardView>(R.id.play_button)
        val randomButton = view.findViewById<CardView>(R.id.random_button)

        val til = view.findViewById(R.id.editNameTextLayout) as TextInputLayout
        val nicknameEditText = view.findViewById(R.id.editNameText) as TextInputEditText

        val filter = InputFilter { source, _, _, _, _, _ ->
            creatingAvatarViewModel.onCorrectText(source)
        }

        nicknameEditText.filters = arrayOf(filter)

        val avatar = Avatar(bodyImageView)

        creatingAvatarViewModel = ViewModelProvider(this).get(CreatingAvatarViewModel::class.java)

        nicknameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                /*NOP*/
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                /*NOP*/
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    creatingAvatarViewModel.onTextChanged(s)
                }
            }

        })

        playButton.setOnClickListener {
            creatingAvatarViewModel.onCreateButton()
        }
        randomButton.setOnClickListener {
            creatingAvatarViewModel.onRandomButton()
        }

        creatingAvatarViewModel.nickNameLiveData.observe(viewLifecycleOwner, Observer {
            nicknameEditText.setText(it)
        })

        creatingAvatarViewModel.isShortNameState.observe(viewLifecycleOwner, Observer {
            if (it) {
                til.isErrorEnabled = true
                til.error = getString(R.string.error_short_nickname)
            } else {
                Log.e("error", "disabled")
                til.isErrorEnabled = false
            }
        })

        creatingAvatarViewModel.allowCreateAvatar.observe(viewLifecycleOwner, Observer {
            if (it) {
                startFirebaseAuth()
            } else {
                //TODO something
            }
        })

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


        creatingAvatarViewModel.fragmentIntentLiveData.observe(viewLifecycleOwner, Observer {
            LocalBroadcastManager.getInstance(requireContext())
                    .sendBroadcast(it)
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