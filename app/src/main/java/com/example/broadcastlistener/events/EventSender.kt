package com.example.broadcastlistener.events

import android.util.Log
import com.example.broadcastlistener.Config
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
                val url = URL(Config.backendUrl)
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

                val body = json.toString(2)
                Log.d("EventSender", "Sending JSON:\n$body")

                connection.outputStream.use { os ->
                    os.write(body.toByteArray(Charsets.UTF_8))
                    os.flush()
                }

                val responseCode = connection.responseCode
                Log.d("EventSender", "Event sent. Response code: $responseCode")

            } catch (e: Exception) {
                Log.e("EventSender", "Error sending event", e)
            }
        }
    }
}
