package com.example.broadcastlistener.deduplication

import com.example.broadcastlistener.events.SystemEvent

object DeduplicationStrategyRegistry {
    private val strategyMap: Map<String, DeduplicationStrategy> = mapOf(
        "android.intent.action.ACTION_POWER_CONNECTED" to PowerEventStrategy,
        "android.intent.action.ACTION_POWER_DISCONNECTED" to PowerEventStrategy,
        "android.intent.action.HEADSET_PLUG" to HeadsetPlugStrategy,
        "android.bluetooth.adapter.action.STATE_CHANGED" to BluetoothStateStrategy,
        "com.example.CUSTOM_EVENT" to CustomEventStrategy
    )

    fun getStrategyFor(event: SystemEvent): DeduplicationStrategy {
        return strategyMap[event.action] ?: DefaultStrategy
    }
}
