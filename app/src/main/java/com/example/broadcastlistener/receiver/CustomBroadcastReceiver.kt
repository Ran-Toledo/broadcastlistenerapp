package com.example.broadcastlistener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CustomBroadcastReceiver : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        val message = intent?.getStringExtra("custom_message")
        Log.d("CustomBroadcastReceiver", "Received custom broadcast: $message")
    }
}