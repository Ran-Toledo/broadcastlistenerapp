package com.example.broadcastlistener.deduplication

import com.example.broadcastlistener.events.SystemEvent
import com.example.broadcastlistener.Config
import java.security.MessageDigest
import java.util.LinkedList

object Deduplicator {
    private const val MAX_CACHE_SIZE = 50
    private val recentHashes = LinkedList<String>()

    fun shouldProcessEvent(event: SystemEvent): Boolean {
        val strategy = DeduplicationStrategyRegistry.getStrategyFor(event)
        val identity = strategy.getEventIdentity(event)
        val hash = sha256(identity)

        if (recentHashes.contains(hash)) {
            return false
        }

        val maxCacheSize = Config.cacheSize.coerceAtMost(MAX_CACHE_SIZE)

        recentHashes.add(hash)
        if (recentHashes.size > maxCacheSize) {
            recentHashes.removeFirst()
        }

        return true
    }

    private fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
