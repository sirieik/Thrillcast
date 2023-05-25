package com.example.thrillcast

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Representerer roten av applikasjonen og er ansvarlig for å initialisere avhengighetsinjeksjon via Hilt.
 *
 * Dette er en Hilt-aktivert Application-undersklasse. Den bruker '@HiltAndroidApp' annotasjonen som utløser Hilt's
 * kodegenereringsfunksjoner, noe som resulterer i opprettelsen av avhengighetsbeholderen på applikasjonsnivå.
 *
 * Selv om klassen ser ut til å være tom, spiller den en avgjørende rolle i å sette opp applikasjonens
 * infrastruktur for avhengighetsinjeksjon.
 */
@HiltAndroidApp
class OurApp : Application()