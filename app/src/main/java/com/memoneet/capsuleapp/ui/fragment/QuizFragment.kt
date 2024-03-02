package com.memoneet.capsuleapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.memoneet.capsuleapp.R
import com.memoneet.capsuleapp.data.model.QuizQuestion
import com.memoneet.capsuleapp.data.model.QuizResult
import com.memoneet.capsuleapp.databinding.FragmentQuizBinding
import com.memoneet.capsuleapp.ui.viewmodel.SharedViewModel

class QuizFragment : Fragment()
{
    private var totalCorrectAns: Int = 0
    private lateinit var binding: FragmentQuizBinding
    private lateinit var currentQuestion: QuizQuestion

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setViewModel()
    }

    private fun setupView()
    {
        binding.bookmarkImageView.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.bookmark), Toast.LENGTH_SHORT).show()
        }
        binding.helpImageView.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.help), Toast.LENGTH_SHORT).show()
        }
        binding.nextImageView.setOnClickListener {
            handleUserAnswer(-1)
        }
    }

    private fun setViewModel()
    {
        sharedViewModel.fetchQuizQuestions()

        sharedViewModel.quizQuestionsLiveData.observe(viewLifecycleOwner) { questions ->
            if (questions.isNotEmpty())
            {
                displayQuestion(questions.first())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayQuestion(question: QuizQuestion)
    {
        val queNo = sharedViewModel.currentQuestionIndex + 1

        binding.questionNumberTextView.text = "Question ${queNo}/${sharedViewModel.quizQuestionsLiveData.value?.size}"

        currentQuestion = question
        binding.questionTextView.text =
            "Q${queNo}: ${question.question}"
        binding.optionsContainer.removeAllViews()
        question.options.forEachIndexed { index, optionText ->
            val optionView = createOptionView(optionText)
            optionView.setOnClickListener {
                handleUserAnswer(index) // Pass the index of the clicked option
            }
            binding.optionsContainer.addView(optionView)
        }
    }

    private fun createOptionView(optionText: String): TextView
    {
        val padding = 16
        val margin = 16

        val optionView = TextView(requireContext())
        optionView.text = optionText
        optionView.textSize = 16f
        optionView.setBackgroundResource(R.drawable.quiz_option_background)
        optionView.gravity = Gravity.CENTER
        optionView.setPadding(padding, padding, padding, padding)

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, margin, 0, margin)
        optionView.layoutParams = layoutParams

        return optionView
    }

    private fun handleUserAnswer(selectedOption: Int)
    {
        val isCorrect = selectedOption == currentQuestion.correctAnswerIndex

        if (isCorrect)
        {
            totalCorrectAns++
        }

        // Check if there are more questions
        val hasNextQuestion = sharedViewModel.hasNextQuestion()

        if (hasNextQuestion)
        {
            // Move to the next question
            val nextQuestion = sharedViewModel.getNextQuestion()
            if (nextQuestion != null)
            {
                displayQuestion(nextQuestion)
            }
        } else
        {
            // All questions answered
            sharedViewModel.quizQuestionsLiveData.value?.size?.let {
                QuizResult(
                    totalCorrectAns,
                    it
                )
            }?.let {
                sharedViewModel.setQuizResult(
                    it
                )
            }
            sharedViewModel.onQuizCompleted()
        }
    }
}
