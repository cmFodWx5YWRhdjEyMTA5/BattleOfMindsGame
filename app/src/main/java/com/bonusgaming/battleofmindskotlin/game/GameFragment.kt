package com.bonusgaming.battleofmindskotlin.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bonusgaming.battleofmindskotlin.R
import com.bonusgaming.battleofmindskotlin.custom_views.ProgressRoundBar
import com.bonusgaming.battleofmindskotlin.custom_views.ProgressRoundBar.TimeOffListener
import kotlinx.android.synthetic.main.fragment_game.*


class GameFragment : Fragment() {

    lateinit var button1: CardView
    lateinit var button2: CardView
    lateinit var button3: CardView
    lateinit var button4: CardView
    lateinit var question: CardView
    val gameModelView: GameModelView = GameModelView()

    private val buttonClickListener = View.OnClickListener {
        val answer = ((it as ViewGroup).getChildAt(0) as TextView).text.toString()
        gameModelView.checkAnswer(answer)
    }

    private fun setAnswersClickable(value: Boolean) {
        button1.isClickable = value
        button2.isClickable = value
        button3.isClickable = value
        button4.isClickable = value
    }

    private fun setQuestionClickable(value: Boolean) {
        question.isClickable = value;

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_game, container, false)
        val textQuestion = view.findViewById<TextView>(R.id.text_question)
        val textAnswer1 = view.findViewById<TextView>(R.id.text_answer_1)
        val textAnswer2 = view.findViewById<TextView>(R.id.text_answer_2)
        val textAnswer3 = view.findViewById<TextView>(R.id.text_answer_3)
        val textAnswer4 = view.findViewById<TextView>(R.id.text_answer_4)
        val tapText = view.findViewById<TextView>(R.id.text_tap_to_next_round)

        val progressBarAnimation = view.findViewById<ProgressRoundBar>(R.id.progressBar)
        progressBarAnimation.timeOffListener = object : TimeOffListener {
            override fun timeOf() {
                gameModelView.onTimeOff()
            }
        }


        button1 = view.findViewById(R.id.button_1)
        button2 = view.findViewById(R.id.button_2)
        button3 = view.findViewById(R.id.button_3)
        button4 = view.findViewById(R.id.button_4)
        question = view.findViewById(R.id.card_question)



        button1.setOnClickListener(buttonClickListener)
        button2.setOnClickListener(buttonClickListener)
        button3.setOnClickListener(buttonClickListener)
        button4.setOnClickListener(buttonClickListener)
        question.setOnClickListener {
            gameModelView.onQuestionClick()
        }


        gameModelView.buttonLiveData1.observe(this, Observer {
            button1.setCardBackgroundColor(it)
        })
        gameModelView.buttonLiveData2.observe(this, Observer {

            button2.setCardBackgroundColor(it)
        })
        gameModelView.buttonLiveData3.observe(this, Observer {
            button3.setCardBackgroundColor(it)
        })
        gameModelView.buttonLiveData4.observe(this, Observer {
            button4.setCardBackgroundColor(it)
        })

        gameModelView.answersClickable.observe(this, Observer {
            setAnswersClickable(it)
        })

        gameModelView.questionClickable.observe(this, Observer {
            setQuestionClickable(it)
        })

        gameModelView.historyEntityLiveData.observe(this, Observer {
            textQuestion.text = it.question
            textAnswer1.text = it.answer_1
            textAnswer2.text = it.answer_2
            textAnswer3.text = it.answer_3
            textAnswer4.text = it.answer_4
        })


        gameModelView.progressBarState.observe(this, Observer {
            when (it) {
                ProgressRoundBar.STOP_STATE -> progressBar.stopAnimate()
            }
        })

        gameModelView.tapTextVisibility.observe(this, Observer {
            tapText.visibility = it
        })

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gameModelView.onViewCreated()
    }


}