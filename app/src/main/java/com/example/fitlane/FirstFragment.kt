package com.example.fitlane

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.fitlane.databinding.FragmentFirstBinding

import android.app.Service
import android.content.Intent
import android.content.IntentFilter

import android.os.IBinder
import com.example.fitlane.databinding.ActivityMainBinding
import java.util.*
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var bindingA: FragmentFirstBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentFirstBinding.inflate(layoutInflater)
        //setCont
        //setContentView(binding.root)

        bindingA.TimerStart.setOnClickListener{startTimer()}
        bindingA.TimerReset.setOnClickListener{resetTimer()}
        bindingA.TimerStop.setOnClickListener{stopTimer()}
        serviceIntent = Intent(applicationContext,TimeService::class.java)
        registerReceiver(updateTime, IntentFilter(TimeService.TIMER_UPDATED))

    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceiver(contaxt: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(TimeService.TIME_EXTRA,0.0)
            binding.timer.text = getTimeStringFromDouble(time)
        }
    }
    private fun getTimeStringFromDouble(time:Double): String
    {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400/3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        return makeTimeString(hours,minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String=String.format("%02d:%02d:%02d")

    private fun startTimer()
    {

    }
    private fun resetTimer()
    {

    }
    private fun stopTimer()
    {

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}