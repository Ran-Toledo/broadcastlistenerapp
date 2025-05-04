package com.example.broadcastlistener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.broadcastlistener.events.EventDispatcher
import com.example.broadcastlistener.events.SystemEvent

class BaseReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let { action ->
            val extras = intent.extras?.keySet()
                ?.associateWith { key -> intent.extras?.get(key) }
                ?: emptyMap()

            val event = SystemEvent(
                action = action,
                timestamp = System.currentTimeMillis(),
                extras = extras
            )

            EventDispatcher.onEventReceived(event)
        }
    }
}
