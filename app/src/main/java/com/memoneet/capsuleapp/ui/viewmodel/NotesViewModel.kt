package com.memoneet.capsuleapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.memoneet.capsuleapp.data.model.Note
import com.memoneet.capsuleapp.data.repository.CapsuleRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: CapsuleRepository) : ViewModel()
{
    private val _note = MutableLiveData<Note>()
    val noteLiveData: LiveData<Note> = _note

    fun fetchNote()
    {
        viewModelScope.launch {
            _note.value = repository.getNote()
        }
    }
}