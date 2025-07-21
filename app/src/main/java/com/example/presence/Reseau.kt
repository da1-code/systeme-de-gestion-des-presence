package com.example.presence
import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import android.widget.Toast

class Reseau(private val context: Context) {

    fun getWifiInfo() {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val connectionInfo = wifiManager.connectionInfo

        val ssid = connectionInfo.ssid.replace("\"", "") // Suppression des guillemets
        val macAddress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectionInfo.macAddress
        } else {
            "MAC Address not accessible"
        }

        fun showWifiInfo() {
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo: android.net.wifi.WifiInfo? = wifiManager.connectionInfo

            if (wifiInfo != null) {
                val ssid = wifiInfo.ssid.replace("\"", "")
                val bssid = wifiInfo.bssid ?: "Non disponible"
                val macAddress = wifiInfo.macAddress

                val message = "SSID: $ssid\nBSSID: $bssid\nMAC: ${macAddress ?: "Non disponible"}"
                Log.d("NetworkInfo", message)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Aucune information WiFi disponible", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


}