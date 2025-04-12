package com.example.broadcastlistener.receiver

import android.util.Log

class EventDispatcher : EventListener
{
    override fun onEventReceived(action: String, extras: Map<String, Any?>)
    {
        Log.d("EventDispatcher", "Event: $action, Extras: $extras")
        // TODO: Deduplication & Backend dispatch
    }
}
