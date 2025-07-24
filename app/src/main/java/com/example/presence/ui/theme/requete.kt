package com.example.presence.ui.theme

import android.R
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Requete(private val context : Context) {
    // Instance de Firestore
    private var db = FirebaseFirestore.getInstance()

    // Méthode pour envoyer les données à Firestore
    fun envoyerVersFirestore(email: String, objet: String, Message: String, identifiants: String) {
        val requeteData = hashMapOf(
            "email" to email,
            "objet" to objet,
            "message" to Message,
            "identifiants" to identifiants
        )

        db.collection("requete") // Remplacez "requêtes" par le nom de votre collection
            .add(requeteData)
            .addOnSuccessListener {
                Toast.makeText(context, "requte envoyer avec succes", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "erreur lors de l'envoi:${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}