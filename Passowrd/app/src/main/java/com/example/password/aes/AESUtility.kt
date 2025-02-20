package com.example.password.aes

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_CBC
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_PKCS7
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AESUtility {
    private val keyStore = KeyStore.getInstance("AppKeyStore").apply {
        load(null)
    }

    private val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
        init(Cipher.ENCRYPT_MODE, getKey())
    }

    private fun getDecryptCipherForIv(iv: ByteArray): Cipher {
        return Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, getKey(), IvParameterSpec(iv))
        }
    }

    private fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry("secret", null) as? KeyStore.SecretKeyEntry;
        return existingKey?.secretKey ?: createKey();
    }

    private fun createKey(): SecretKey {
        return KeyGenerator.getInstance(ALGORITHM).apply {
            init(
                KeyGenParameterSpec
                    .Builder("secret", PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()
    }

    fun encrypt(byte: ByteArray, outputStream: OutputStream): ByteArray {
        val encryptedBytes = encryptCipher.doFinal(byte);
        outputStream.use {
            it.write(encryptCipher.iv.size);
            it.write(encryptCipher.iv);
            it.write(encryptedBytes.size);
            it.write(encryptedBytes);
        }
        return encryptedBytes;
    }

    fun decrypt(inputStream: InputStream): ByteArray {
        return inputStream.use {
            val ivSize = it.read()
            val iv = ByteArray(ivSize)
            it.read(iv)

            val encryptedBytesSize = it.read()
            val encryptedBytes = ByteArray(encryptedBytesSize)
            it.read(encryptedBytes)

            getDecryptCipherForIv(iv).doFinal(encryptedBytes)
        }
    }

    companion object {
        private const val ALGORITHM = KEY_ALGORITHM_AES;
        private const val BLOCK_MODE = BLOCK_MODE_CBC;
        private const val PADDING = ENCRYPTION_PADDING_PKCS7;
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING";
    }
}