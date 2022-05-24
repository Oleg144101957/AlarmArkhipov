package com.vishnevskiypro.alarmarkhipov

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mainButton: Button

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainButton = findViewById(R.id.mainButton)
        mainButton.setOnClickListener {
            val materialTimePicker: MaterialTimePicker = MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select a time")
                .build()

            materialTimePicker.show(supportFragmentManager, "TIME_PICKER")

            materialTimePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.MINUTE, materialTimePicker.minute)
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.hour)

                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val alarmClockInfo = AlarmManager.AlarmClockInfo(calendar.timeInMillis, getAlarmInfoPendingIntent())

                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent())
                Toast.makeText(this, "Alarm", Toast.LENGTH_LONG).show()


            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun getAlarmInfoPendingIntent(): PendingIntent{
        val alarmInfoIntent = Intent(this, MainActivity::class.java)
        alarmInfoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getActivity(this, 0, alarmInfoIntent, PendingIntent.FLAG_MUTABLE)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun getAlarmActionPendingIntent(): PendingIntent{
        val intent = Intent(this, AlarmActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

        return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_MUTABLE)
    }

}