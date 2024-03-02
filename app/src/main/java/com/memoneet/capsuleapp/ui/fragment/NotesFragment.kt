package com.memoneet.capsuleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.memoneet.capsuleapp.R
import com.memoneet.capsuleapp.data.repository.MockCapsuleRepository
import com.memoneet.capsuleapp.databinding.FragmentNotesBinding
import com.memoneet.capsuleapp.ui.activity.CapsuleActivity
import com.memoneet.capsuleapp.ui.util.Const
import com.memoneet.capsuleapp.ui.util.ViewModelFactory
import com.memoneet.capsuleapp.ui.viewmodel.NotesViewModel

class NotesFragment : Fragment()
{
    private lateinit var binding: FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModels{
        ViewModelFactory(MockCapsuleRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupLiveData()
    }

    private fun setupLiveData()
    {
        viewModel.fetchNote()
        viewModel.noteLiveData.observe(viewLifecycleOwner) { note ->
            binding.notesTextView.text = note.content
        }
    }

    private fun setupView()
    {
        binding.upnextLayout.upNextTitleTextView.text = resources.getString(R.string.quiz)
        binding.upnextLayout.upNextDescriptionTextView.text = resources.getString(R.string.quiz_desc)

        binding.upnextLayout.upNextCardView.setOnClickListener {
            (requireActivity() as CapsuleActivity).setCurrentPagerItem(Const.Pages.PAGE_QUIZ)
        }
    }
}