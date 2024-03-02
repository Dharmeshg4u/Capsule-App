package com.memoneet.capsuleapp.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.memoneet.capsuleapp.R
import com.memoneet.capsuleapp.data.repository.MockCapsuleRepository
import com.memoneet.capsuleapp.databinding.ActivityCapsuleBinding
import com.memoneet.capsuleapp.ui.adapter.CapsulePagerAdapter
import com.memoneet.capsuleapp.ui.util.Const
import com.memoneet.capsuleapp.ui.util.ViewModelFactory
import com.memoneet.capsuleapp.ui.viewmodel.SharedViewModel

class CapsuleActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityCapsuleBinding
    private lateinit var pagerAdapter: CapsulePagerAdapter
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var timerCountDownTimer: CountDownTimer
    private val timeInMinute = 10L //adjust time

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCapsuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set actionbar with title
        setMyActionbar(resources.getString(R.string.app_name))

        // basic initialization
        setupViews()

        // Setup view pager and tabs
        setupViewPager()

        // Setup shared view model
        setupViewModel()

        // Start the timer
        startTimer()
    }

    private fun setupViews()
    {
        //setupViews
    }

    private fun setupViewModel()
    {
        sharedViewModel = ViewModelProvider(
            this,
            ViewModelFactory(MockCapsuleRepository())
        )[SharedViewModel::class.java]

        sharedViewModel.quizCompletionLiveData.observe(this) { quizCompleted ->
            if (quizCompleted)
            {
                // The quiz is completed
                binding.viewPager.currentItem = 3
            }
        }
    }

    private fun setupViewPager()
    {
        pagerAdapter = CapsulePagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = pagerAdapter.getTitle(position)
            if (!pagerAdapter.isTabVisible(position))
            {
                tab.view.visibility = GONE
            }
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
        {
            override fun onPageSelected(position: Int)
            {
                if (position == Const.Pages.PAGE_QUIZ || position == Const.Pages.PAGE_QUIZ_RESULT)
                {
                    binding.tabs.visibility = GONE
                    binding.viewPager.isUserInputEnabled = false

                    if (position == Const.Pages.PAGE_QUIZ_RESULT)
                    {
                        timerCountDownTimer.cancel()
                    }
                } else
                {
                    binding.tabs.visibility = VISIBLE
                    binding.viewPager.isUserInputEnabled = true
                }
            }
        })
    }

    private fun startTimer()
    {
        timerCountDownTimer = object : CountDownTimer(timeInMinute * 60 * 1000, 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = millisUntilFinished / 1000 % 60
                binding.timerTextView.text = String.format("%02d:%02d min", minutes, seconds)
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish()
            {
                binding.timerTextView.text = "00:00 min"
                showTimerFinishAlert()
            }
        }.start()
    }

    private fun showTimerFinishAlert()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Time's Up!")
        builder.setMessage("The timer has finished.")
        builder.setCancelable(false)
        builder.setPositiveButton("Exit") { dialog, _ ->
            dialog.dismiss()
            this@CapsuleActivity.finish()
        }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(this, R.color.brand))
    }

    fun setCurrentPagerItem(position: Int)
    {
        binding.viewPager.currentItem = position
    }

    private fun setMyActionbar(title: String)
    {
        setSupportActionBar(binding.toolbar)
        val actionbar = supportActionBar
        actionbar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            this.title = title // Set the title here
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId)
        {
            android.R.id.home ->
            {
                // Handle home icon click here
                this@CapsuleActivity.finish()
                true
            }
            // Handle other menu item clicks if needed
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        timerCountDownTimer.cancel()
    }

}
