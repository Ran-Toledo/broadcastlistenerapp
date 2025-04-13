package com.example.broadcastlistener

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.health.connect.datatypes.units.Power
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.example.broadcastlistener.receiver.AirplaneModeReceiver
import com.example.broadcastlistener.receiver.BluetoothStateReceiver
import com.example.broadcastlistener.ui.theme.BroadcastListenerAppTheme
import com.example.broadcastlistener.receiver.CustomBroadcastReceiver
import com.example.broadcastlistener.receiver.EventDispatcher
import com.example.broadcastlistener.receiver.HeadsetReceiver
import com.example.broadcastlistener.receiver.PowerReceiver
import com.example.broadcastlistener.utils.BroadcastSender

class MainActivity : ComponentActivity() {

    private val dispatcher = EventDispatcher()

    private val airplaneModeReceiver = AirplaneModeReceiver(dispatcher)
    private val customBroadcastReceiver = CustomBroadcastReceiver(dispatcher)
    private val headsetReceiver = HeadsetReceiver(dispatcher)
    private val powerConnectedReceiver = PowerReceiver(dispatcher)
    private val powerDisconnectedReceiver = PowerReceiver(dispatcher)
    private val bluetoothStateChangedReceiver = BluetoothStateReceiver(dispatcher)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        registerReceiver(headsetReceiver, IntentFilter(Intent.ACTION_HEADSET_PLUG))
        registerReceiver(powerConnectedReceiver, IntentFilter(Intent.ACTION_POWER_CONNECTED))
        registerReceiver(powerDisconnectedReceiver, IntentFilter(Intent.ACTION_POWER_DISCONNECTED))

        var filter = IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(bluetoothStateChangedReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            registerReceiver(bluetoothStateChangedReceiver, filter)
        }

        filter = IntentFilter("com.example.CUSTOM_EVENT")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(customBroadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            @Suppress("UnspecifiedRegisterReceiverFlag")
            registerReceiver(customBroadcastReceiver, filter)
        }

        enableEdgeToEdge()

        setContent {
            BroadcastListenerAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Hello, Android!")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            BroadcastSender.sendCustomEvent(this@MainActivity, "Hello from UI button!")
                        }) {
                            Text(text = "Send Custom Broadcast")
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
        unregisterReceiver(customBroadcastReceiver)
        unregisterReceiver(headsetReceiver)
        unregisterReceiver(powerConnectedReceiver)
        unregisterReceiver(powerDisconnectedReceiver)
        unregisterReceiver(bluetoothStateChangedReceiver)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BroadcastListenerAppTheme {
        Greeting("Android")
    }
}