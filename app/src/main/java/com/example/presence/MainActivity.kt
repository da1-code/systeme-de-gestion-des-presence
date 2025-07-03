package com.example.presence

import android.content.ContentValues.TAG
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
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "DANIE"
        user["last"] = "Love"
        user["born"] = 18


// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
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