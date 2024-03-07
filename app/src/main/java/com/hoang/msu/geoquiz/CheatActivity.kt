package com.hoang.msu.geoquiz

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hoang.msu.geoquiz.databinding.ActivityCheatBinding
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels

private const val EXTRA_ANSWER_IS_TRUE = "com.hoang.msu.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.hoang.msu.geoquiz.answer_shown"


class CheatActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCheatBinding
    private val viewModel: CheatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener {
            viewModel.btnClicked = true
            updateAnswer()
        }
        if(viewModel.btnClicked){
            updateAnswer()
        }

    }
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }
    private fun updateAnswer() {
        val answerText = when {
            viewModel.answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        binding.answerTextView.setText(answerText)
        setAnswerShownResult(true)
    }


    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}