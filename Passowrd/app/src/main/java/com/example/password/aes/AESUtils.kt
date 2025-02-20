package com.example.password.aes

import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object AESUtils {
    private val secretKey: SecretKey = generateKey() // Generate a random key (store securely)
    private val iv = ByteArray(16) // Initialization vector (IV) - Should be random for each encryption

    private fun generateKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256) // 256-bit key for stronger encryption
        return keyGen.generateKey()
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding") // Use CBC mode
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv)) // Use IV
        val encryptedBytes = cipher.doFinal(input.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT) // Encode to Base64
    }

    fun decrypt(input: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv)) // Use IV
        val decodedBytes = Base64.decode(input, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes, Charsets.UTF_8) // Convert back to string
    }
}
