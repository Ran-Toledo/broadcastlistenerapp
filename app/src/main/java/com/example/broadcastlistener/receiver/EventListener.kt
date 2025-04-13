package com.example.broadcastlistener.receiver

import com.example.broadcastlistener.model.SystemEvent

interface EventListener {
    fun onEventReceived(event: SystemEvent)
}
