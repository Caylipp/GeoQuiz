package com.hoang.msu.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hoang.msu.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0

    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate (Bundle?) called?")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener{
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener{
            nextQuestion()
        }

        binding.questionTextview.setOnClickListener{
            nextQuestion()
        }


        updateQuestion() //necessary??


    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextview.setText(questionTextResId)
        enableInputBtn()
    }
    private fun nextQuestion() {
        currentIndex++
        if(currentIndex == questionBank.size){
            showScore()
        }
        currentIndex %= questionBank.size
        updateQuestion()
    }
    private fun checkAnswer(userAnswer: Boolean) {
        disableInputBtn()
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            score++
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()

    }
    private fun showScore(){
        val scorePercent = (score.toFloat() / questionBank.size.toFloat()) * 100
        val endScore = String.format("%.1f%%", scorePercent)
        Toast.makeText(this, endScore, Toast.LENGTH_SHORT)
            .show()
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