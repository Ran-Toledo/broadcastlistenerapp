package com.example.broadcastlistener.events

import android.content.Context
import android.content.Intent

object CustomEventSender
{
    fun sendCustomEvent(context: Context, message: String)
    {
        val intent = Intent("com.example.CUSTOM_EVENT")
        intent.setPackage(context.packageName)
        intent.putExtra("custom_message", message)
        context.sendBroadcast(intent)
    }
}
