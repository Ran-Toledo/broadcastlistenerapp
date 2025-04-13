package com.example.broadcastlistener.deduplication

import com.example.broadcastlistener.events.SystemEvent

interface DeduplicationStrategy {
    fun getEventIdentity(event: SystemEvent): String
}
