package com.milanradosavac.textpad.funkcija_obrade_teksta.prezentacija.generalne_korisne_klase_i_objekti

sealed class Ekran(private val ruta: String, val ime: String) {

    object GlavniEkran: Ekran("glavni_ekran", "Pocetak")

    object EkranZaMenadzmentOnlajnSinhronizacije: Ekran("ekran_za_menadzment_onlajn_sinhronizacije", "Onlajn Sinhronizacija")

    object InformacioniEkran: Ekran("informacioni_ekran", "Informacije")

    open operator fun invoke() = this.ruta
}
