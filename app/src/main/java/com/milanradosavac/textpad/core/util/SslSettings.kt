package com.milanradosavac.textpad.core.util

import android.content.Context
import com.milanradosavac.textpad.R
import org.koin.java.KoinJavaComponent.inject
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object SslSettings {

    val context: Context by inject(Context::class.java)

    fun getKeyStore(): KeyStore {
        val keyStoreFile = context.resources.openRawResource(R.raw.android_keystore)
        val keyStorePassword = "Maskedred123".toCharArray()
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getInstance("PKCS12").type)
        keyStore.load(keyStoreFile, keyStorePassword)
        return keyStore
    }

    fun getTrustManagerFactory(): TrustManagerFactory? {
        val trustManagerFactory = TrustManagerFactory.getInstance("RSA", "AndroidOpenSSL")
        trustManagerFactory.init(getKeyStore())
        return trustManagerFactory
    }

    fun getSslContext(): SSLContext? {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(KeyManagerFactory.getInstance("RSA").keyManagers, getTrustManagerFactory()?.trustManagers, null)
        return sslContext
    }

    fun getTrustManager(): X509TrustManager {
        return getTrustManagerFactory()?.trustManagers?.first { it is X509TrustManager } as X509TrustManager
    }
}