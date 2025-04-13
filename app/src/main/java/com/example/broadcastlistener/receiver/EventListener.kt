package com.example.broadcastlistener.receiver

import com.example.broadcastlistener.events.SystemEvent

interface EventListener {
    fun onEventReceived(event: SystemEvent)
}
