package com.bonusgaming.battleofmindskotlin.game
//
//import android.annotation.SuppressLint
//import android.graphics.Color
//import android.util.Log
//import android.view.View
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.bonusgaming.battleofmindskotlin.FragmentState
//import com.bonusgaming.battleofmindskotlin.custom_views.ProgressRoundBar
//import io.reactivex.Completable
//import io.reactivex.Observable
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.Disposable
//import io.reactivex.functions.Consumer
//import io.reactivex.schedulers.Schedulers
//import java.util.*
//import java.util.concurrent.TimeUnit
//import javax.inject.Inject
//import kotlin.collections.ArrayList
//import kotlin.collections.HashMap
//
//const val KEY_ROUND = "key_round"
//const val MAX_ROUND = 6
//
//class GameViewModel @Inject constructor(private val model: GameModel) : ViewModel() {
//
//    lateinit var disposable: Disposable
//    var currentRound: Int = -1
//    val progressBarState = MutableLiveData<Int>()
//    val buttonLiveData1 = MutableLiveData<Int>()
//    val buttonLiveData2 = MutableLiveData<Int>()
//    val buttonLiveData3 = MutableLiveData<Int>()
//    val buttonLiveData4 = MutableLiveData<Int>()
//    val answersClickable = MutableLiveData<Boolean>()
//    val questionClickable = MutableLiveData<Boolean>()
//    val tapTextVisibility = MutableLiveData<Int>()
//
//    var answers: HashMap<String, WrapperLiveAndCorrect> = HashMap()
//    var rightAnswers: HashMap<String, WrapperLiveAndCorrect> = HashMap()
//
//    fun onViewCreated() {
//        initRoundValue()
//        increaseRound()
//
//        tapTextVisibility.value = View.INVISIBLE
//        questionClickable.value = false
//    }
//
//    fun onTimeOff() {
//        answersClickable.value = false
//        showRightAnswer(Color.BLUE)
//        tapTextShow()
//    }
//
//    @SuppressLint("CheckResult")
//    fun tapTextShow() {
//        Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe {
//                    Log.e("1122", "tapTextShow $it")
//                    if (it % 2 == 0L) tapTextVisibility.value = View.VISIBLE
//                    else tapTextVisibility.value = View.INVISIBLE
//                }
//        questionClickable.value = true
//        increaseRound()
//    }
//
//    fun initRoundValue() {
////        currentRound = model.prefs.getInt(KEY_ROUND)
//        if (currentRound == -1) currentRound = 1
//        Log.e("1155", "current round $currentRound")
//    }
//
//    fun increaseRound() {
////        if (currentRound < MAX_ROUND) {
////            val nextRound = currentRound + 1
////            model.prefs.putInt(KEY_ROUND, nextRound)
////        } else
////            model.prefs.putInt(KEY_ROUND, -1)
//    }
//
//    fun onQuestionClick() {
////        if (currentRound < MAX_ROUND)
////            model.setCurrentState(FragmentState.GAME)
////        else if (currentRound == MAX_ROUND)
////            model.setCurrentState(FragmentState.MAIN)
//    }
//
//    @SuppressLint("CheckResult")
//    fun checkAnswer(answer: String) {
//        progressBarState.value = ProgressRoundBar.STOP_STATE
//        answersClickable.value = false
//        Log.e("1122", "checkanswer $answer")
//        Log.e("1122", "checkanswer ${rightAnswers[answer]?.liveData}")
//        rightAnswers[answer]?.liveData?.value = Color.YELLOW
//        Observable.just(answer).delay(3, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe {
//                    changeColor(it)
//                    tapTextShow()
//                }
//    }
//
//    @SuppressLint("CheckResult")
//    fun changeColor(clickedButton: String) {
//
//        val rightValue = rightAnswers[clickedButton]
//        rightValue?.liveData?.value = if (rightValue!!.right) Color.GREEN else Color.RED
//        // Log.e("123", "clicked $clickedButton  and ${rightValue.right}")
//        //rightAnswers.remove(clickedButton)
//        showRightAnswer(Color.GREEN)
//    }
//
//    fun showRightAnswer(color: Int) {
//        rightAnswers.forEach { (key, value) ->
//
//            Log.e("123", "for $key and ${value.right}")
//            if (value.right) {
//                Observable.interval(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).take(7)
//                        .subscribe(Consumer { t ->
//                            Log.e("1133", "WORK RX $t")
////                            if (t % 2 == 0L) value.liveData.value = color
////                            else value.liveData.value = model.getCardColor()
//                        })
//
//            }
//
//        }
//    }
//
//
//    private fun changeStateInModelDelay() {
//        disposable = Completable.complete().delay(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    Log.e("123231", "changeStateInModelDelay")
////                    model.setCurrentState(FragmentState.GAME)
//                }
//    }
//
//    inner class WrapperLiveAndCorrect(var liveData: MutableLiveData<Int>, var right: Boolean)
//}
