package com.memoneet.capsuleapp.ui.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.memoneet.capsuleapp.data.repository.CapsuleRepository
import com.memoneet.capsuleapp.ui.viewmodel.NotesViewModel
import com.memoneet.capsuleapp.ui.viewmodel.SharedViewModel
import com.memoneet.capsuleapp.ui.viewmodel.VideoViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: CapsuleRepository) : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        return when
        {
            modelClass.isAssignableFrom(VideoViewModel::class.java) ->
            {
                VideoViewModel(repository) as T
            }

            modelClass.isAssignableFrom(NotesViewModel::class.java) ->
            {
                NotesViewModel(repository) as T
            }

            modelClass.isAssignableFrom(SharedViewModel::class.java) ->
            {
                SharedViewModel(repository) as T
            }

            else ->
            {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

