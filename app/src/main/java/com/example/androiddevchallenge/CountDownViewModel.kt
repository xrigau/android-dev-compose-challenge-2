package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class CountDownViewModel : ViewModel() {

    private val timeFormatter = SimpleDateFormat("mm:ss.S", Locale.getDefault())

    private val _countDownInMillis = MutableLiveData<Long>()
    val countDownFormattedTime: LiveData<String> =
        Transformations.map(_countDownInMillis) { timeInMillis ->
            timeFormatter.format(timeInMillis)
        }

    private val _isCounting = MutableLiveData<Boolean>()
    val isCounting: LiveData<Boolean> = _isCounting

    fun startCountDown(countDownTime: Long) {
        _countDownInMillis.value = countDownTime

        val countDownTimer = object : CountDownTimer(countDownTime, 100) {
            override fun onTick(millisUntilFinished: Long) {
                _countDownInMillis.value = millisUntilFinished
            }

            override fun onFinish() {
                _countDownInMillis.value = 0
                _isCounting.value = false
            }
        }
        _isCounting.value = true
        countDownTimer.start()
    }
}