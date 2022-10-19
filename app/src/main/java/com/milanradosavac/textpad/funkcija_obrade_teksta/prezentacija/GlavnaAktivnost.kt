package com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija.generalne_korisne_klase_i_objekti.Navigacija
import com.milanradosavac.textpad.ki.tema.TextPadTema

class GlavnaAktivnost : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextPadTema {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigacija(navigacioniKontroler = rememberNavController(), stanjeMenija = rememberDrawerState(initialValue = DrawerValue.Closed))
                }
            }
        }
    }
}