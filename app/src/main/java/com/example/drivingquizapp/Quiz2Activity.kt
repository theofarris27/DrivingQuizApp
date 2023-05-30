package com.example.drivingquizapp

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import com.example.drivingquizapp.databinding.ActivityQuiz1Binding
import com.example.drivingquizapp.databinding.ActivityQuiz2Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Quiz2Activity: AppCompatActivity() {

    private lateinit var binding: ActivityQuiz2Binding
    private lateinit var quiz: Quiz

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuiz2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        loadQuestions()
        binding.textViewQuestionNum.text = quiz.currentQuestion.toString()
        binding.textViewQuestion.text = quiz.updateText()
        binding.buttonMenuButton.visibility = View.GONE
        binding.progressBar2.progress = 0
        binding.progressBar2.max = quiz.totalQuestions

        binding.textViewAnswer1.text = "A: " + quiz.getSample("answer1")
        binding.textViewAnswer2.text = "B: " + quiz.getSample("answer2")
        binding.textViewAnswer3.text = "C: " + quiz.getSample("answer3")
        binding.textViewAnswer4.text = "D: " + quiz.getSample("answer4")

        binding.buttonA.setOnClickListener{
            quiz.checkTrue('A')
            binding.progressBar2.progress = quiz.currentQuestion
            endChecker()
        }
        binding.buttonB.setOnClickListener{
            quiz.checkTrue('B')
            binding.progressBar2.progress = quiz.currentQuestion
            endChecker()

        }
        binding.buttonC.setOnClickListener{
            quiz.checkTrue('C')
            binding.progressBar2.progress = quiz.currentQuestion
            endChecker()

        }
        binding.buttonD.setOnClickListener{
            quiz.checkTrue('D')
            binding.progressBar2.progress = quiz.currentQuestion
            endChecker()
        }

    }

    private fun loadQuestions(){
        val inputStream = resources.openRawResource(R.raw.quiz2)
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

    private fun endChecker(){
        if(quiz.checkEnd()) {
            if(quiz.passed()){
                binding.textViewQuestion.text = "Final Score: " + quiz.score + ". You Passed! Congrats!"
                binding.progressBar2.progress = binding.progressBar2.max
            }
            else{
                binding.textViewQuestion.text = "Final Score: " + quiz.score + ". You Failed :("
                binding.progressBar2.progress = binding.progressBar2.max
            }
            binding.endGroup.visibility = View.INVISIBLE
            binding.buttonMenuButton.visibility = View.VISIBLE
            binding.buttonMenuButton.setOnClickListener {
                if(quiz.passed()){

                    val sharedPref = getSharedPreferences(
                        getString(R.string.preferenceFileKey), Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putBoolean(getString(R.string.quiz2key), true)
                        commit()
                    }
                }

                finish()}
        }
        else{
            binding.textViewQuestion.text = quiz.updateText()
            binding.textViewQuestionNum.text = "#" + quiz.currentQuestion.toString()
            binding.textViewAnswer1.text = "A: " + quiz.getSample("answer1")
            binding.textViewAnswer2.text = "B: " + quiz.getSample("answer2")
            binding.textViewAnswer3.text = "C: " + quiz.getSample("answer3")
            binding.textViewAnswer4.text = "D: " + quiz.getSample("answer4")
        }
    }

}