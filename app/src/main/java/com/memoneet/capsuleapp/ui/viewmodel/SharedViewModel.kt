package com.memoneet.capsuleapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoneet.capsuleapp.data.model.QuizQuestion
import com.memoneet.capsuleapp.data.model.QuizResult
import com.memoneet.capsuleapp.data.repository.CapsuleRepository
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: CapsuleRepository) : ViewModel()
{
    var currentQuestionIndex: Int = 0

    private val _quizQuestions = MutableLiveData<List<QuizQuestion>>()
    val quizQuestionsLiveData: LiveData<List<QuizQuestion>> = _quizQuestions

    private val _quizResult = MutableLiveData<QuizResult>()
    val quizResultLiveData: LiveData<QuizResult> = _quizResult

    private val _quizCompletionLiveData = MutableLiveData<Boolean>()
    val quizCompletionLiveData: LiveData<Boolean> = _quizCompletionLiveData

    fun fetchQuizQuestions()
    {
        viewModelScope.launch {
            _quizQuestions.value = repository.getQuizQuestions()
        }
    }

    fun setQuizResult(quizResult: QuizResult)
    {
        _quizResult.value = quizResult
    }

    fun hasNextQuestion(): Boolean
    {
        return currentQuestionIndex < (_quizQuestions.value?.size?.minus(1) ?: 0)
    }

    fun getNextQuestion(): QuizQuestion?
    {
        val questions = _quizQuestions.value ?: return null
        return if (hasNextQuestion())
        {
            currentQuestionIndex++
            questions[currentQuestionIndex]
        } else
        {
            null
        }
    }
    fun onQuizCompleted() {
        _quizCompletionLiveData.value = true
    }
}
