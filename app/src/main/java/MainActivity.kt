package com.example.presence

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.presence.ui.theme.PresenceTheme
import com.example.presence.ui.theme.Requete
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var equipementManager: EquipementManager
    private lateinit var reseau: Reseau
    private lateinit var requete: Requete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reseau = Reseau(this)

        // Obtenir les informations du Wi-Fi
       // val wifiInfo = reseau.getWifiInfo()

        // Uncomment this section to use EquipementManager and Firestore
        /*
        equipementManager = EquipementManager(this)
        equipementManager.ajouterEquipement("pc","mac","salle")

        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "DANIE"
        user["last"] = "Love"
        user["born"] = 18

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
        */


            //requete= Requete(this)
            //requete.envoyerVersFirestore("eden2@gamil.com","demande de conge","bienhd","s45SShd")


    }
}


