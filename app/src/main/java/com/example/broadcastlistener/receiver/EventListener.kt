package com.example.broadcastlistener.receiver

interface EventListener
{
    fun onEventReceived(action: String, extras: Map<String, Any?>)
}
