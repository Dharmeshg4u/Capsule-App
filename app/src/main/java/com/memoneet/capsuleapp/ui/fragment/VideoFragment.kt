package com.memoneet.capsuleapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.memoneet.capsuleapp.R
import com.memoneet.capsuleapp.data.repository.MockCapsuleRepository
import com.memoneet.capsuleapp.databinding.FragmentVideoBinding
import com.memoneet.capsuleapp.ui.activity.CapsuleActivity
import com.memoneet.capsuleapp.ui.util.Const
import com.memoneet.capsuleapp.ui.util.ViewModelFactory
import com.memoneet.capsuleapp.ui.viewmodel.VideoViewModel

class VideoFragment : Fragment()
{
    private lateinit var binding: FragmentVideoBinding

    private val viewModel: VideoViewModel by viewModels {
        ViewModelFactory(MockCapsuleRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentVideoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupView()

        setupLiveData()
    }

    private fun setupView()
    {
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)

        binding.videoView.setMediaController(mediaController)
        binding.videoView.setOnPreparedListener {
            binding.progressBar.visibility = View.GONE
        }

        binding.upnextLayout.upNextTitleTextView.text = resources.getString(R.string.notes)
        binding.upnextLayout.upNextDescriptionTextView.text = resources.getString(R.string.notes_desc)

        binding.upnextLayout.upNextCardView.setOnClickListener {
            (requireActivity() as CapsuleActivity).setCurrentPagerItem(Const.Pages.PAGE_NOTES)
        }
    }

    private fun setupLiveData()
    {
        viewModel.fetchVideo()

        viewModel.videoLiveData.observe(viewLifecycleOwner) { video ->
            if (video != null)
            {
                binding.videoView.setVideoPath(video.url)
                binding.videoView.start()
            }
        }
    }
}