// dispatcher/EventDispatcher.kt
package com.example.broadcastlistener.dispatcher

import android.util.Log
import com.example.broadcastlistener.model.SystemEvent
import com.example.broadcastlistener.receiver.EventListener
import com.example.broadcastlistener.repository.EventSender
import com.example.broadcastlistener.utils.Deduplicator

object EventDispatcher : EventListener {
    override fun onEventReceived(event: SystemEvent) {
        if (Deduplicator.shouldProcessEvent(event)) {
            Log.d("EventDispatcher", "Dispatching event: $event")
            EventSender.sendEvent(event)
        } else {
            Log.d("EventDispatcher", "Duplicate event skipped: ${event.action}")
        }
    }
}
