package com.bonusgaming.battleofmindskotlin.game
/*
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


    lateinit var gameViewModel: GameViewModel

    private val buttonClickListener = View.OnClickListener {
        val answer = ((it as ViewGroup).getChildAt(0) as TextView).text.toString()
        gameViewModel.checkAnswer(answer)
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
                gameViewModel.onTimeOff()
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
            gameViewModel.onQuestionClick()
        }


        gameViewModel.buttonLiveData1.observe(viewLifecycleOwner, Observer {
            button1.setCardBackgroundColor(it)
        })
        gameViewModel.buttonLiveData2.observe(viewLifecycleOwner, Observer {

            button2.setCardBackgroundColor(it)
        })
        gameViewModel.buttonLiveData3.observe(viewLifecycleOwner, Observer {
            button3.setCardBackgroundColor(it)
        })
        gameViewModel.buttonLiveData4.observe(viewLifecycleOwner, Observer {
            button4.setCardBackgroundColor(it)
        })

        gameViewModel.answersClickable.observe(viewLifecycleOwner, Observer {
            setAnswersClickable(it)
        })

        gameViewModel.questionClickable.observe(viewLifecycleOwner, Observer {
            setQuestionClickable(it)
        })


        gameViewModel.progressBarState.observe(viewLifecycleOwner, Observer {
            when (it) {
                ProgressRoundBar.STOP_STATE -> progressBar.stopAnimate()
            }
        })

        gameViewModel.tapTextVisibility.observe(viewLifecycleOwner, Observer {
            tapText.visibility = it
        })

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gameViewModel.onViewCreated()
    }


}*/
