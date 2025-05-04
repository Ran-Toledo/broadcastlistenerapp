package com.example.broadcastlistener.events

import android.util.Log
import com.example.broadcastlistener.deduplication.Deduplicator

object EventDispatcher {
    fun onEventReceived(event: SystemEvent) {
        if (Deduplicator.shouldProcessEvent(event)) {
            Log.d("EventDispatcher", "Dispatching event: $event")
            EventSender.sendEvent(event)
        } else {
            Log.d("EventDispatcher", "Duplicate event skipped: ${event.action}")
        }
    }
}
