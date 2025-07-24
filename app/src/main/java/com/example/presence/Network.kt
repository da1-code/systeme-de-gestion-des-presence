package com.example.presence

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class NetWork(private val context: Context) {

    private val db = FirebaseFirestore.getInstance()

    fun verifierConnexionEntreprise(onResult: (Boolean) -> Unit) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo

        if (wifiInfo != null) {
            val bssid = wifiInfo.bssid?.lowercase() ?: ""

            Log.d("NetworkCheck", "Vérification BSSID: $bssid")

            db.collection("equipements")
                .whereEqualTo("mac", bssid)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        Toast.makeText(context, "Connecté à un WiFi de l’entreprise", Toast.LENGTH_SHORT).show()
                        onResult(true)
                        enregistrerWifiInfo(wifiInfo) // Appel de la fonction pour enregistrer les informations Wi-Fi
                    } else {
                        Toast.makeText(context, "Veuillez vous connecter au WiFi de l’entreprise", Toast.LENGTH_LONG).show()
                        onResult(false)
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Erreur Firestore : ${e.message}", Toast.LENGTH_LONG).show()
                    onResult(false)
                }
        } else {
            Toast.makeText(context, "Impossible de lire les informations WiFi", Toast.LENGTH_SHORT).show()
            onResult(false)
        }
    }

    private fun listerEquipements() {
        val equipementManager = EquipementManager(context)
        equipementManager.listerEquipements { equipements ->
            equipements.forEach { equipement ->
                Log.d("EquipementInfo", "Nom: ${equipement.nom}, MAC: ${equipement.mac}, Localisation: ${equipement.localisation}")
                Toast.makeText(context, "Nom: ${equipement.nom}, MAC: ${equipement.mac}, Localisation: ${equipement.localisation}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showWifiInfo() {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo

        if (wifiInfo != null) {
            val ssid = wifiInfo.ssid.replace("\"", "")
            val bssid = wifiInfo.bssid ?: "Non disponible"
            val macAddress = wifiInfo.macAddress

            val message = "SSID: $ssid\nBSSID: $bssid\nMAC: ${macAddress ?: "Non disponible"}"
            Log.d("NetworkInfo", message)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Aucune information Wi-Fi disponible", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enregistrerWifiInfo(wifiInfo: WifiInfo) {
        val ssid = wifiInfo.ssid.replace("\"", "")
        val bssid = wifiInfo.bssid ?: "Non disponible"
        val macAddress = wifiInfo.macAddress ?: "Non disponible"

        val wifiData = hashMapOf(
            "ssid" to ssid,
            "bssid" to bssid,
            "macAddress" to macAddress
        )

        db.collection("wifi_info")
            .add(wifiData)
            .addOnSuccessListener {
                Log.d("WifiInfo", "Informations Wi-Fi enregistrées avec succès.")
            }
            .addOnFailureListener { e ->
                Log.e("WifiInfo", "Erreur lors de l'enregistrement des informations Wi-Fi: ${e.message}")
            }
    }
}