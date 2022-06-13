package com.apps.backgroundservice

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
 //       openSamsungBackgroundSettings()
        val FPS = 40
        val update: TimerTask = UpdateTask()
        timer.scheduleAtFixedRate(update, 0, 5000)
        val textView: TextView = findViewById(R.id.t1)
        textView.setOnClickListener {

         /*   var i = Intent()
            i.action = Intent.ACTION_VIEW
            val managerclock = packageManager
            i = managerclock.getLaunchIntentForPackage("com.whatsapp")!!
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            startActivity(i)*/

       /*     var intent = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
            if (intent != null) {
           //     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else {
                // Bring user to the market or let them choose an app?
                intent = Intent(Intent.ACTION_VIEW)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.data = Uri.parse("market://details?id=" + "com.google.android.youtube")
                startActivity(intent)
            }*/
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timerTaskObj: TimerTask = object : TimerTask() {
                override fun run() {
                    applicationContext.startForegroundService(
                        Intent(
                            applicationContext,
                            YourService::class.java
                        )
                    )

                }
            }
            timerTaskObj.run()
            //    this.startForegroundService(Intent(this, YourService::class.java))
        } else {
            this.startService(Intent(this, YourService::class.java))
        }

    }

    fun openSamsungBackgroundSettings() {

        val possibleIntents = mutableListOf<Intent>()
        val battery1 = "com.samsung.android.sm.ui.battery.BatteryActivity"
        val battery2 = "com.samsung.android.sm.battery.ui.BatteryActivity"
        val pkg = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
            "com.samsung.android.lool"
        else
            "com.samsung.android.sm"
        possibleIntents.add(Intent().setComponent(ComponentName(pkg, battery1)))
        possibleIntents.add(Intent().setComponent(ComponentName(pkg, battery2)))
        //general settings as backup
        possibleIntents.add(Intent(Settings.ACTION_SETTINGS))
        possibleIntents.forEach {
            try {
                startActivity(it)
                return
            } catch (ex: Exception) {
                //    w(ex){"Failed to open intent:$it"}
            }
        }
    }

    class UpdateTask : TimerTask() {
        override fun run() {
            //   this.startForegroundService(Intent(this, YourService::class.java))
        }
    }
}