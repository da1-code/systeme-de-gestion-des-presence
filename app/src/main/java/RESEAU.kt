package com.example.presence

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build

data class WifiInfo(
    val ssid: String?,
    val macAddress: String?,
    val ipAddress: String?
)

class RESEAU(private val context: Context) {

    fun getWifiInfo(): WifiInfo {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectionInfo = wifiManager.connectionInfo

        val ssid = connectionInfo.ssid.replace("\"", "") // Suppression des guillemets
        val macAddress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectionInfo.macAddress
        } else {
            "MAC Address not accessible"
        }
        val ipAddress = intToIp(connectionInfo.ipAddress)

        return WifiInfo(
            ssid = ssid,
            macAddress = macAddress,
            ipAddress = ipAddress
        )
    }

    private fun intToIp(ip: Int): String {
        return (ip shr 0 and 0xff).toString() + "." +
                (ip shr 8 and 0xff).toString() + "." +
                (ip shr 16 and 0xff).toString() + "." +
                (ip shr 24 and 0xff).toString()
    }
}

