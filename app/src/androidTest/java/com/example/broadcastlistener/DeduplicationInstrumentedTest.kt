package com.example.broadcastlistener

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.broadcastlistener.events.EventDispatcher
import com.example.broadcastlistener.events.SystemEvent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeduplicationInstrumentedTest {

    @Before
    fun setup() {
    }

    @Test
    fun identicalBluetoothEvents_shouldBeDeduplicated() {
        val event = SystemEvent(
            action = "android.bluetooth.adapter.action.STATE_CHANGED",
            timestamp = System.currentTimeMillis(),
            extras = mapOf("android.bluetooth.adapter.extra.STATE" to 12)
        )

        EventDispatcher.onEventReceived(event)
        EventDispatcher.onEventReceived(event)
    }

    @Test
    fun identicalPowerConnectedEvents_shouldBeDeduplicated() {
        val event = SystemEvent(
            action = "android.intent.action.ACTION_POWER_CONNECTED",
            timestamp = System.currentTimeMillis(),
            extras = emptyMap()
        )

        EventDispatcher.onEventReceived(event)
        EventDispatcher.onEventReceived(event)
    }

    @Test
    fun identicalPowerDisconnectedEvents_shouldBeDeduplicated() {
        val event = SystemEvent(
            action = "android.intent.action.ACTION_POWER_DISCONNECTED",
            timestamp = System.currentTimeMillis(),
            extras = emptyMap()
        )

        EventDispatcher.onEventReceived(event)
        EventDispatcher.onEventReceived(event)
    }

    @Test
    fun identicalHeadsetPlugEvents_shouldBeDeduplicated() {
        val event = SystemEvent(
            action = "android.intent.action.HEADSET_PLUG",
            timestamp = System.currentTimeMillis(),
            extras = mapOf("state" to 1, "microphone" to 1)
        )

        EventDispatcher.onEventReceived(event)
        EventDispatcher.onEventReceived(event)
    }

    @Test
    fun identicalCustomEvents_shouldBeDeduplicated() {
        val event = SystemEvent(
            action = "com.example.CUSTOM_EVENT",
            timestamp = System.currentTimeMillis(),
            extras = mapOf("custom_message" to "Hello from UI button!")
        )

        EventDispatcher.onEventReceived(event)
        EventDispatcher.onEventReceived(event)
    }

    @Test
    fun differentEvents_shouldNotBeDeduplicated() {
        val event1 = SystemEvent(
            action = "android.intent.action.ACTION_POWER_CONNECTED",
            timestamp = System.currentTimeMillis(),
            extras = emptyMap()
        )
        val event2 = SystemEvent(
            action = "android.intent.action.ACTION_POWER_DISCONNECTED",
            timestamp = System.currentTimeMillis(),
            extras = emptyMap()
        )

        EventDispatcher.onEventReceived(event1)
        EventDispatcher.onEventReceived(event2)
    }
}
