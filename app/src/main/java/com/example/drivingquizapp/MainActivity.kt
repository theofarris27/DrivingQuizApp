package com.example.drivingquizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.drivingquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var quiz1: Button
    lateinit var quiz2: Button
    lateinit var quiz3: Button
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wireWidgets()

        quiz1.setOnClickListener{
            val intentThis = Intent(this, Quiz1Activity::class.java).apply{
            }
            startActivity(intentThis)
        }
        quiz2.setOnClickListener{
            val intent = Intent(this, Quiz2Activity::class.java).apply {
            }
            startActivity(intent)
        }
        quiz3.setOnClickListener {
            val intent = Intent(this, Quiz3Activity::class.java).apply{}
            startActivity(intent)
        }
        onStart()
        }


        private fun wireWidgets() {
            quiz1 = findViewById(R.id.button_quiz1)
            quiz2 = findViewById(R.id.button_quiz2)
            quiz3 = findViewById(R.id.button_quiz3)
        }

    override fun onStart() {
        val sharedPref = getSharedPreferences(
            getString(R.string.preferenceFileKey), Context.MODE_PRIVATE)
        val quiz1finished = sharedPref.getBoolean(getString(R.string.quiz1key),false)
        val quiz2finished = sharedPref.getBoolean(getString(R.string.quiz2key),false)
        Log.d("MainActivity", "onStart: quiz1finished: $quiz1finished")
        if(!quiz1finished){
            quiz2.visibility = View.INVISIBLE
            quiz3.visibility = View.INVISIBLE
        }
        else{
            quiz2.visibility = View.VISIBLE
        }
        if(!quiz2finished){
            quiz3.visibility = View.INVISIBLE
        }
        else{
            quiz3.visibility = View.VISIBLE
        }
        super.onStart()
    }

    }
