package com.example.broadcastlistener.utils

import android.content.Context
import android.content.Intent

object BroadcastSender
{
    fun sendCustomEvent(context: Context, message: String)
    {
        val intent = Intent("com.example.CUSTOM_EVENT")
        intent.setPackage(context.packageName)
        intent.putExtra("custom_message", message)
        context.sendBroadcast(intent)
    }
}
