package com.example.broadcastlistener.utils

import com.example.broadcastlistener.model.SystemEvent
import java.security.MessageDigest
import java.util.LinkedList

object Deduplicator {
    private const val MAX_CACHE_SIZE = 50
    private val recentHashes = LinkedList<String>()

    fun shouldProcessEvent(event: SystemEvent): Boolean {
        val hash = hashEvent(event)

        if (recentHashes.contains(hash)) {
            return false // Duplicate
        }

        recentHashes.add(hash)
        if (recentHashes.size > MAX_CACHE_SIZE) {
            recentHashes.removeFirst()
        }

        return true
    }

    private fun hashEvent(event: SystemEvent): String {
        val filteredExtras = event.extras.toSortedMap().toString()
        val raw = "${event.action}|$filteredExtras"

        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(raw.toByteArray())

        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
