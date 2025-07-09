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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.presence.ui.theme.PresenceTheme
import com.example.presence.ui.theme.Requete
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {

     private lateinit var requete: Requete

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            requete= Requete(this)
            requete.envoyerVersFirestore("eden2@gamil.com","demande de conge","bienhd","s45SShd")
        enableEdgeToEdge()
        setContent {
            PresenceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
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
    PresenceTheme {
        Greeting("Android")
    }


}