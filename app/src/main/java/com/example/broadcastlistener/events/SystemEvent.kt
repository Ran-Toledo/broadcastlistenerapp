package com.example.broadcastlistener.events

data class SystemEvent(
    val action: String,
    val timestamp: Long,
    val extras: Map<String, Any?>
) {
    override fun hashCode(): Int {
        return action.hashCode() * 31 + extras.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is SystemEvent &&
                action == other.action &&
                extras == other.extras
    }
}
