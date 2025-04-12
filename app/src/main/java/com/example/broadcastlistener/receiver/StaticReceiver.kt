package com.example.broadcastlistener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StaticReceiver : BroadcastReceiver()
{
    private val dispatcher = EventDispatcher()

    override fun onReceive(context: Context?, intent: Intent?)
    {
        intent?.action?.let { action ->
            val extras = intent.extras?.keySet()?.associateWith { intent.extras?.get(it) } ?: emptyMap()
            dispatcher.onEventReceived(action, extras)
        }
    }
}
