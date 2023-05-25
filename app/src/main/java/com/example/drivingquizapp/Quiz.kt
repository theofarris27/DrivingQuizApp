package com.example.drivingquizapp

class Quiz(val questions: List <Question>) {
    var score = 0
    val totalQuestions = questions.size
    var currentQuestion = 0

    fun checkTrue(input: Char) : Boolean{
        if(getAnswer()==input){
            score++
            currentQuestion++
            return true
        }
        else{
            return false
        }
    }
    fun getAnswer(): Char{
        return questions.get(currentQuestion).answer!!
    }
    fun updateText(): String{
        return questions.get(currentQuestion).question!!
    }
    fun returnImage(): String{
        return questions.get(currentQuestion).imageUrl!!
    }
    fun getSample(input: String): String{
        when (input) {
            "answer1" -> return questions.get(currentQuestion).answer1!!
            "answer2" -> return questions.get(currentQuestion).answer2!!
            "answer3" -> return questions.get(currentQuestion).answer3!!
            "answer4" -> return questions.get(currentQuestion).answer4!!
        }
        return "answer1"
    }
    fun passed() : Boolean{
        return(score >= 8)
    }
    fun checkEnd(): Boolean {
        if (totalQuestions <= currentQuestion + 1) {
            return true
        }
        else{
            return false
        }
    }
}