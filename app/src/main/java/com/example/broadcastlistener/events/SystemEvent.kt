package com.example.broadcastlistener.events

data class SystemEvent(
    val action: String,
    val timestamp: Long,
    val extras: Map<String, Any?>
)
