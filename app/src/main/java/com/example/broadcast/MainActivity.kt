package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                progressBar.progress = percent
            }
        }
    }

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Intent().apply {
                action = mMyReceiver.ACTION_CLICKED
                putExtra(MyReceiver.EXTRA_COUNT, ++count)
                sendBroadcast(this)
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(MyReceiver.ACTION_CLICKED)
            addAction("loaded")
        }
        registerReceiver(receiver, intentFilter)
        Intent(this, MyService::class.java).apply {
            startService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}