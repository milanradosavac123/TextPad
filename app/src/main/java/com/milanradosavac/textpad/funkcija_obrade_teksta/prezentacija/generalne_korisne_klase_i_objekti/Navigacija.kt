package com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija.generalne_korisne_klase_i_objekti

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.DrawerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija.ekrani.ekran_za_menadzment_onlajn_sinhronizacije.EkranZaMenadzmentOnlajnSinhronizacije
import com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija.ekrani.glavni_ekran.GlavniEkran
import com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija.ekrani.informacioni_ekran.InformacioniEkran
import kotlinx.coroutines.launch

@Composable
fun Navigacija(
    navigacioniKontroler: NavHostController,
    stanjeMenija: DrawerState
) {

    val obim = rememberCoroutineScope()
    val oblikMenija = MaterialTheme.shapes.medium.copy(
        topStart = CornerSize(0.dp),
        bottomStart = CornerSize(0.dp)
    )

    ModalDrawer(
        drawerContent = {
            arrayOf(
                Ekran.GlavniEkran,
                Ekran.EkranZaMenadzmentOnlajnSinhronizacije,
                Ekran.InformacioniEkran
            ).forEachIndexed { i, it ->

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp, top = 5.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                        .clickable {
                            if (navigacioniKontroler.currentDestination?.route != it()) {
                                navigacioniKontroler.apply {
                                    popBackStack()
                                    navigate(it())

                                    obim.launch {
                                        stanjeMenija.close()
                                    }

                                    return@clickable
                                }
                            }

                            obim.launch {
                                stanjeMenija.close()
                            }
                        }
                ) {
                    Text(text = "- ${it.ime}", color = MaterialTheme.colors.onPrimary, modifier = Modifier
                        .padding(start = 5.dp)
                    )
                }
            }
        },
        drawerShape = oblikMenija,
        scrimColor = Color.Transparent,
        drawerState = stanjeMenija
    ) {

        NavHost(
            navController = navigacioniKontroler,
            startDestination = Ekran.GlavniEkran()
        ) {

            composable(Ekran.GlavniEkran()) { GlavniEkran() }

            composable(Ekran.EkranZaMenadzmentOnlajnSinhronizacije()) { EkranZaMenadzmentOnlajnSinhronizacije() }

            composable(Ekran.InformacioniEkran()) { InformacioniEkran() }

        }

    }
}