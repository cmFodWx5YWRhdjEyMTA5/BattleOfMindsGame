package com.bonusgaming.battleofmindskotlin.features.login.creating_avatar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
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
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApi
import com.bonusgaming.battleofmindskotlin.base_db_api.DbApiProvider
import com.bonusgaming.battleofmindskotlin.base_ui.di.component.UiComponent
import com.bonusgaming.battleofmindskotlin.base_ui.sendIntentForNextState
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApi
import com.bonusgaming.battleofmindskotlin.base_web_api.WebApiProvider
import com.bonusgaming.battleofmindskotlin.core.main.PathProvider
import com.bonusgaming.battleofmindskotlin.core.main.ViewModelFactory
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFeature
import com.bonusgaming.battleofmindskotlin.core.main.di.scope.PerFragment
import com.bonusgaming.battleofmindskotlin.core.main.mediator.AppFacadeProvider
import com.bonusgaming.battleofmindskotlin.features.login.R
import com.bonusgaming.battleofmindskotlin.features.login.di.component.CreatingAvatarComponent
import com.bonusgaming.battleofmindskotlin.features.login.di.component.LoginComponent
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import javax.inject.Inject

@PerFragment
class CreatingAvatarFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var pathProvider: PathProvider

    @Inject
    lateinit var picasso: Picasso

    lateinit var creatingAvatarViewModel: CreatingAvatarViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_creating_avatar, container,false)
    }

    companion object {
        private const val RC_SIGN_IN = 22
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val uiComponent = UiComponent.get((requireActivity().application as AppFacadeProvider).provideAppFacade())

//        CreatingAvatarComponent

        LoginComponent.get(uiComponent,
                        (requireActivity().application as DbApiProvider).provideDbApi(),
                        (requireActivity().application as WebApiProvider).provideWebApi())
                .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("9789", "-----------------CreatingAvatarFragment onViewCreated $viewModelFactory")
        creatingAvatarViewModel = ViewModelProvider(this, viewModelFactory)[CreatingAvatarViewModel::class.java]

        val bodyImageView = view.findViewById<ImageView>(R.id.image_body)
        val leftButton = view.findViewById<CardView>(R.id.arrow_left)
        val rightButton = view.findViewById<CardView>(R.id.arrow_right)
        val playButton = view.findViewById<CardView>(R.id.play_button)
        val randomButton = view.findViewById<CardView>(R.id.random_button)
        val til = view.findViewById(R.id.editNameTextLayout) as TextInputLayout
        val nicknameEditText = view.findViewById(R.id.editNameText) as TextInputEditText

        fun setViewListeners() {
            val filter = InputFilter { source, _, _, _, _, _ ->
                creatingAvatarViewModel.onCorrectText(source)
            }

            nicknameEditText.filters = arrayOf(filter)

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

            leftButton.setOnClickListener {
                creatingAvatarViewModel.onLeftButton()
            }

            rightButton.setOnClickListener {
                if (::creatingAvatarViewModel.isInitialized)
                    creatingAvatarViewModel.onRightButton()
            }
        }

        fun setObservers() {

            creatingAvatarViewModel.nickNameLiveData.observe(viewLifecycleOwner, Observer {
                nicknameEditText.setText(it)
            })

            creatingAvatarViewModel.inflateAvatar.observe(viewLifecycleOwner, Observer {
                picasso.load(it)
                        .into(bodyImageView)
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

            creatingAvatarViewModel.fragmentIntentLiveData.observe(viewLifecycleOwner, Observer {
                sendIntentForNextState(it)
            })

            creatingAvatarViewModel.allowCreateAvatar.observe(viewLifecycleOwner, Observer {
                @Suppress("ControlFlowWithEmptyBody")
                if (it) {
                    startFirebaseAuth()
                } else {
                    //TODO something
                }
            })
        }
        setViewListeners()
        setObservers()
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