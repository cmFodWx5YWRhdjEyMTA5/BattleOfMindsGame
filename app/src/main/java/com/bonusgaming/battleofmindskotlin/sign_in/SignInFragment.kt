package com.bonusgaming.battleofmindskotlin.sign_in

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bonusgaming.battleofmindskotlin.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_sign_in_container, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        Log.e("testauth", "${FirebaseAuth.getInstance().currentUser?.email}")
        signInViewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            var stateView: View? = null
            when (it) {
                SignInViewModel.STATE_SIGN_IN -> {
                    stateView = layoutInflater.inflate(R.layout.sign_in_auth, null)
                    val buttonSignIn = stateView.findViewById<CardView>(R.id.sign_in_button)
                    buttonSignIn.setOnClickListener {
                        startFirebaseUI()
                    }
                    val buttonSignInAnonym =
                        stateView.findViewById<CardView>(R.id.sign_in_anonymously_button)
                    buttonSignInAnonym.setOnClickListener {
                        signInViewModel.onPlayAnomymouslyClick()
                    }
                }

                SignInViewModel.STATE_HELLO -> {
                    stateView = layoutInflater.inflate(R.layout.sign_in_hello, null)
                }
            }
            (view as ViewGroup).addView(stateView)

        })

    }


}