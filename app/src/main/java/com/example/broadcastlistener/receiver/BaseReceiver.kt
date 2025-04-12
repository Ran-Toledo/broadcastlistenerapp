package com.example.broadcastlistener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

abstract class BaseReceiver(private val listener: EventListener) : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?)
    {
        intent?.action?.let { action ->
            val extras = intent.extras?.keySet()?.associateWith { intent.extras?.get(it) } ?: emptyMap()
            listener.onEventReceived(action, extras)
        }
    }
}
