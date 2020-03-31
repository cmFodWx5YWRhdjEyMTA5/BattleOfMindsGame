package com.bonusgaming.battleofmindskotlin.features.loading.shadows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bonusgaming.battleofmindskotlin.features.loading.R
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsFragment
import com.bonusgaming.battleofmindskotlin.features.loading.presentation.LoadingAssetsViewModel
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow

@Implements(LoadingAssetsFragment::class)
class LoadingAssetsFragmentShadow : Shadow() {

    lateinit var viewModel: LoadingAssetsViewModel

    @RealObject
    lateinit var realFragment: LoadingAssetsFragment

    @Implementation
    protected fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download_assets, container, false)
    }
}