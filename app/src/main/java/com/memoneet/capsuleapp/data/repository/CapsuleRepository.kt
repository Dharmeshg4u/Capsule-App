package com.memoneet.capsuleapp.data.repository

import com.memoneet.capsuleapp.data.model.Note
import com.memoneet.capsuleapp.data.model.QuizQuestion
import com.memoneet.capsuleapp.data.model.Video

interface CapsuleRepository {
    suspend fun getVideo(): Video
    suspend fun getNote(): Note
    suspend fun getQuizQuestions(): List<QuizQuestion>
}