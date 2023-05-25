package com.example.thrillcast

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

/**
 * En oppstartsaktivitet som viser en SplashScreen før overgangen til MainActivity.
 * Denne aktiviteten er annotert for å undertrykke "CustomSplashScreen" lint advarsel,
 * ettersom egendefinerte oppstartsskjermer generelt frarådes til fordel for den nye SplashScreen APIen
 * som kun kan kjøres på API-level 31 eller høyere.
 *
 * @property activityScope en CoroutineScope bundet til livssyklusen til aktiviteten for å starte coroutiner.
 *
 * @see AppCompatActivity
 */
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {


    private val activityScope = CoroutineScope(Dispatchers.Main)

    /**
     * Setter SplashScreen-ens layout og en forsinket overgang på 3 sekunder til hovedaktiviteten.
     * Starter en coroutine for å forsinke overgangen, og tillater dermed en ikke-blokkerende forsinkelse.
     *
     * @param savedInstanceState Hvis aktiviteten blir initialisert på nytt etter tidligere å ha blitt stengt,
     * så inneholder denne Bundle dataene den sist leverte. Merk: Ellers er den null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        activityScope.launch {
            delay(3000)
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    /**
     * Avbryter activityScope når aktiviteten blir ødelagt for å forhindre potensielle minnelekkasjer.
     */
    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()
    }
}