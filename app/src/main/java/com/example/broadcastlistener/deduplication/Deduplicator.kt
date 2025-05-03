package com.example.broadcastlistener.deduplication

import com.example.broadcastlistener.events.SystemEvent
import java.security.MessageDigest

object Deduplicator {
    private val seenEvents = mutableSetOf<SystemEvent>()

    fun shouldProcessEvent(event: SystemEvent): Boolean {
        // val strategy = DeduplicationStrategyRegistry.getStrategyFor(event)
        // val identity = strategy.getEventIdentity(event)
        // val hash = sha256(identity)

        if (!seenEvents.contains(event)) {
            seenEvents.add(event)
            return true
        }

        return false
    }

//    private fun sha256(input: String): String {
//        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
//        return bytes.joinToString("") { "%02x".format(it) }
//    }
}
