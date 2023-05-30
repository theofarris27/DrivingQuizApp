package com.example.drivingquizapp

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.example.drivingquizapp.databinding.ActivityQuiz1Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Quiz1Activity: AppCompatActivity() {

    private lateinit var binding: ActivityQuiz1Binding
    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuiz1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        loadQuestions()
        binding.textViewQuestionNum.text = quiz.currentQuestion.toString()
        binding.textViewQuestion.text = quiz.updateText()
        binding.buttonMenuButton.visibility = View.GONE
        binding.progressBar.progress = 0
        binding.progressBar.max = quiz.totalQuestions

        binding.textViewAnswer1.text = "A: " + quiz.getSample("answer1")
        binding.textViewAnswer2.text = "B: " + quiz.getSample("answer2")
        binding.textViewAnswer3.text = "C: " + quiz.getSample("answer3")
        binding.textViewAnswer4.text = "D: " + quiz.getSample("answer4")

        binding.buttonA.setOnClickListener{

            binding.progressBar.progress = quiz.currentQuestion
            endChecker('A')

        }
        binding.buttonB.setOnClickListener{
            binding.progressBar.progress = quiz.currentQuestion
            endChecker('B')

        }
        binding.buttonC.setOnClickListener{
            binding.progressBar.progress = quiz.currentQuestion
            endChecker('C')

        }
        binding.buttonD.setOnClickListener{
            binding.progressBar.progress = quiz.currentQuestion
            endChecker('D')
        }

    }

    private fun loadQuestions(){
        val inputStream = resources.openRawResource(R.raw.quiz1)
        val jsonString = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }

        Log.d(TAG, "onCreate: $jsonString")
        val gson = Gson()
        val type = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<List<Question>>(jsonString, type)
        quiz = Quiz(questions)
    }

    private fun endChecker(input: Char){
        quiz.checkTrue(input)
        if(quiz.checkEnd()) {
            if(quiz.passed()){
                binding.textViewQuestion.text = "Final Score: " + quiz.score + ". You Passed! Congrats!"
                binding.progressBar.progress = binding.progressBar.max
            }
            else{
                binding.textViewQuestion.text = "Final Score: " + quiz.score + ". You Failed :("
                binding.progressBar.progress = binding.progressBar.max
            }
            binding.endGroup.visibility = View.INVISIBLE
            binding.buttonMenuButton.visibility = View.VISIBLE
            binding.buttonMenuButton.setOnClickListener {
                if(quiz.passed()){

                val sharedPref = getSharedPreferences(
                    getString(R.string.preferenceFileKey), Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean(getString(R.string.quiz1key), true)
                    commit()
                }
                }

                finish()}
        }
        else{
           Log.d("in the else","we get to this point")
            binding.textViewQuestion.text = quiz.updateText()
            binding.textViewQuestionNum.text =  quiz.currentQuestion.toString()
            binding.textViewAnswer1.text = "A: " + quiz.getSample("answer1")
            binding.textViewAnswer2.text = "B: " + quiz.getSample("answer2")
            binding.textViewAnswer3.text = "C: " + quiz.getSample("answer3")
            binding.textViewAnswer4.text = "D: " + quiz.getSample("answer4")
            Log.d("this happens again", "after the update")
        }
    }

}