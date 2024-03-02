package com.memoneet.capsuleapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoneet.capsuleapp.data.model.Video
import com.memoneet.capsuleapp.data.repository.CapsuleRepository
import kotlinx.coroutines.launch

class VideoViewModel(private val repository: CapsuleRepository) : ViewModel()
{
    private val _video = MutableLiveData<Video>()
    val videoLiveData: LiveData<Video> = _video

    fun fetchVideo()
    {
        viewModelScope.launch {
            _video.value = repository.getVideo()
        }
    }
}