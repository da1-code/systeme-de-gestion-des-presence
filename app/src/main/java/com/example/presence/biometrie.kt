package com.example.tonappbiometrique

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class Biometrie(private val context: Context) {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var executor: Executor
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    @RequiresApi(Build.VERSION_CODES.P)
    fun createBiometricPrompt() {
        // Créer un exécuteur pour le thread principal
        executor = ContextCompat.getMainExecutor(context)

        // Configurer le callback pour l'authentification
        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // Gérer l'action du bouton négatif (ex : utilisateur a annulé)
                } else {
                    // Gérer d'autres erreurs d'authentification
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Gérer l'échec de l'authentification
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // Gérer l'authentification réussie
            }
        }

        // Initialiser le BiometricPrompt
        biometricPrompt = BiometricPrompt(context as androidx.fragment.app.FragmentActivity, executor, callback)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun showBiometricPrompt() {
        // Configurer les informations du prompt
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Connexion Biométrique")
            .setSubtitle("Connectez-vous avec votre empreinte digitale")
            .setNegativeButtonText("Utiliser le mot de passe")
            .build()

        // Afficher le prompt d'authentification
        biometricPrompt.authenticate(promptInfo)
    }
}