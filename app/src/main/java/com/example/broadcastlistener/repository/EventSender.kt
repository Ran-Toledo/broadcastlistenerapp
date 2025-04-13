package com.example.broadcastlistener.repository

import android.util.Log
import com.example.broadcastlistener.model.SystemEvent
import com.example.broadcastlistener.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object EventSender {
    fun sendEvent(event: SystemEvent) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(Config.webhookUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                val json = JSONObject()
                json.put("action", event.action)
                json.put("timestamp", event.timestamp)

                val extrasJson = JSONObject()
                for ((key, value) in event.extras) {
                    extrasJson.put(key, value.toString())
                }

                json.put("extras", extrasJson)

                val body = json.toString()
                connection.outputStream.write(body.toByteArray())
                connection.outputStream.flush()
                connection.outputStream.close()

                val responseCode = connection.responseCode
                Log.d("EventSender", "Event sent. Response code: $responseCode")

            } catch (e: Exception) {
                Log.e("EventSender", "Error sending event", e)
            }
        }
    }
}
