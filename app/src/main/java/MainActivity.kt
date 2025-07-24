package com.example.presence

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.presence.ui.theme.PresenceTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var equipementManager: EquipementManager
    private lateinit var netWork: NetWork

    // Enregistreur pour gérer les résultats des demandes de permission
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val locationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val wifiStateGranted = permissions[Manifest.permission.ACCESS_WIFI_STATE] == true

        if (locationGranted && wifiStateGranted) {
            // Permissions accordées
            netWork = NetWork(this)
            netWork.showWifiInfo()
            enregistrerWifiInfo() // Enregistrer les informations Wi-Fi après avoir obtenu les permissions
        } else {
            Log.w("MainActivity", "Autorisation de localisation ou d'accès Wi-Fi refusée")
            Toast.makeText(this, "Aucun enregistrement effectué", Toast.LENGTH_SHORT).show()
            displayNoWifiInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Demander les autorisations nécessaires
        requestPermissions()

        setContent {
            PresenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Initialiser l'UI si nécessaire
                }
            }
        }
    }

    // Méthode pour demander les autorisations
    private fun requestPermissions() {
        requestPermissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
        ))
    }

    // Nouvelle méthode pour enregistrer les informations Wi-Fi
    private fun enregistrerWifiInfo() {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo? = wifiManager.connectionInfo

        if (wifiInfo != null) {
            // Utiliser EquipementManager pour enregistrer les informations
            equipementManager = EquipementManager(this)
            val ssid = wifiInfo.ssid.replace("\"", "")
            val macAddress = wifiInfo.macAddress ?: "Non disponible"

            equipementManager.ajouterEquipement(
                nom = ssid,
                mac = macAddress,
                localisation = "Localisation par défaut" // Remplacez par une valeur appropriée
            )
        } else {
            Toast.makeText(this, "Impossible de récupérer les informations Wi-Fi", Toast.LENGTH_SHORT).show()
        }
    }

    // Méthode pour afficher un message d'absence d'informations Wi-Fi
    private fun displayNoWifiInfo() {
        setContent {
            PresenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text(
                        text = "Aucune information Wi-Fi disponible",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}