package com.example.broadcastlistener.model

data class SystemEvent(
    val action: String,
    val timestamp: Long,
    val extras: Map<String, Any?>
)
