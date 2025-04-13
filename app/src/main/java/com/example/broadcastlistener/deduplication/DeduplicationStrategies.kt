package com.example.broadcastlistener.deduplication

import com.example.broadcastlistener.events.SystemEvent

object PowerEventStrategy : DeduplicationStrategy {
    override fun getEventIdentity(event: SystemEvent): String {
        val state = event.extras["seq"]
        return "${event.action}|$state"
    }
}

object HeadsetPlugStrategy : DeduplicationStrategy {
    override fun getEventIdentity(event: SystemEvent): String {
        val state = event.extras["state"]
        return "${event.action}|$state"
    }
}

object BluetoothStateStrategy : DeduplicationStrategy {
    override fun getEventIdentity(event: SystemEvent): String {
        val state = event.extras["android.bluetooth.adapter.extra.STATE"]
        return "${event.action}|$state"
    }
}

object CustomEventStrategy : DeduplicationStrategy {
    override fun getEventIdentity(event: SystemEvent): String {
        return "${event.action}|${event.extras.toSortedMap()}"
    }
}

object DefaultStrategy : DeduplicationStrategy {
    override fun getEventIdentity(event: SystemEvent): String {
        return "${event.action}|${event.extras.toSortedMap()}"
    }
}
