package com.example.broadcastlistener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.broadcastlistener.events.SystemEvent

class BaseReceiver(private val listener: EventListener) : BroadcastReceiver() {
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

            listener.onEventReceived(event)
        }
    }
}
