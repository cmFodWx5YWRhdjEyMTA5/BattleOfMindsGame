package com.bonusgaming.battleofmindskotlin.loading_assets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bonusgaming.battleofmindskotlin.MainContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadingAssetsViewModel : MainContract.ViewModel() {

    val textStatusLiveData = MutableLiveData<String>()
    val progressLiveData = MutableLiveData<Int>()

    override fun onViewCreated() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (i in 0..100 step 10) {
                    Thread.sleep(1000)
                    withContext(Dispatchers.Main) {
                        progressLiveData.value = i
                        textStatusLiveData.value = "загрузка $i%"
                    }
                }
            }

        }
    }
}