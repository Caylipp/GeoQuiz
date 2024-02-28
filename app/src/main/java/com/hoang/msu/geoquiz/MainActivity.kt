package com.hoang.msu.geoquiz

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.hoang.msu.geoquiz.databinding.ActivityMainBinding
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var score = 0
    private val quizViewModel : QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(

        ActivityResultContracts.StartActivityForResult()
    ) { result ->
// Handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called?")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }

        binding.cheatButton.setOnClickListener(){
            //val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //startActivity(intent)
            cheatLauncher.launch(intent)

        }

        binding.nextButton.setOnClickListener{
            nextQuestion()
        }

        binding.questionTextview.setOnClickListener{
            nextQuestion()
        }


        updateQuestion()


    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextview.setText(questionTextResId)
        enableInputBtn()
    }
    private fun nextQuestion() {
        if((quizViewModel.currentQuestionIndex+1) == quizViewModel.currentQuestionSize){
            showScore()
        }
        quizViewModel.moveToNext()
        updateQuestion()
    }
    private fun checkAnswer(userAnswer: Boolean) {
        disableInputBtn()
        val correctAnswer = quizViewModel.currentQuestionAnswer

//        val messageResId = if (userAnswer == correctAnswer) {
//            score++
//            R.string.correct_toast
//        } else {
//            R.string.incorrect_toast
//        }
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

    }
    private fun showScore(){
        val scorePercent = (score.toFloat() / quizViewModel.currentQuestionSize.toFloat()) * 100
        val endScore = String.format("%.1f%%", scorePercent)
        Toast.makeText(this, endScore, Toast.LENGTH_SHORT)
            .show()
        score = 0
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun enableInputBtn(){
        binding.falseButton.isEnabled = true
        binding.trueButton.isEnabled = true
    }
    private fun disableInputBtn(){
        binding.falseButton.isEnabled = false
        binding.trueButton.isEnabled = false
    }
}