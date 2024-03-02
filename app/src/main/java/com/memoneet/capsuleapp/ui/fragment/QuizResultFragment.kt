package com.memoneet.capsuleapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.memoneet.capsuleapp.databinding.FragmentQuizResultBinding
import com.memoneet.capsuleapp.ui.viewmodel.SharedViewModel

class QuizResultFragment : Fragment()
{
    private lateinit var binding: FragmentQuizResultBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupLiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun setupLiveData()
    {
        viewModel.quizResultLiveData.observe(viewLifecycleOwner) { result ->
            binding.scoreTextView.text = "${result.score}/${result.totalQuestions}"
        }
    }

    private fun setupView()
    {
        binding.homeTextView.setOnClickListener {
            val intent = requireActivity().intent
            requireActivity().finish()
            startActivity(intent)
        }
    }
}