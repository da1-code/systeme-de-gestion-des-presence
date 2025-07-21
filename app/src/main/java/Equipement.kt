package com.example.presence
import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore

class EquipementManager(private val context: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val collectionName = "equipements"
    fun ajouterEquipement(nom: String, mac: String, localisation: String) {
        val data = hashMapOf(
            "nom" to nom,
            "mac" to mac,
            "localisation" to localisation
        )
        db.collection(collectionName)
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(context, "equipement ajouté", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erreur d'ajout:${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun modifierEquipement(documentId: String, nom: String, mac: String, localisation: String) {
        val updateData = mapOf(
            "nom" to nom,
            "mac" to mac,
            "localisation" to localisation
        )
        db.collection(collectionName)
            .document(documentId)
            .set(updateData)
            .addOnSuccessListener {
                Toast.makeText(context, "equipement modifié", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erreur de modification:${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    fun supprimerEquipement(documentId: String) {
        db.collection(collectionName)
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "equipement supprimé", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erreur de suppression:${it.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}

