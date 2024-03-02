package com.memoneet.capsuleapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.memoneet.capsuleapp.ui.fragment.NotesFragment
import com.memoneet.capsuleapp.ui.fragment.QuizFragment
import com.memoneet.capsuleapp.ui.fragment.QuizResultFragment
import com.memoneet.capsuleapp.ui.fragment.VideoFragment
import com.memoneet.capsuleapp.ui.util.Const

class CapsulePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity)
{
    private val tabTitles = arrayOf("Video", "Notes", "Quiz", "Result")

    override fun getItemCount(): Int
    {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment
    {
        return when (position)
        {
            Const.Pages.PAGE_VIDEO -> VideoFragment()
            Const.Pages.PAGE_NOTES -> NotesFragment()
            Const.Pages.PAGE_QUIZ -> QuizFragment()
            Const.Pages.PAGE_QUIZ_RESULT -> QuizResultFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    fun getTitle(position: Int): String
    {
        return tabTitles[position]
    }

    fun isTabVisible(position: Int): Boolean
    {
        return position != Const.Pages.PAGE_QUIZ_RESULT
    }

    companion object
    {
        private const val NUM_PAGES = 4
    }
}