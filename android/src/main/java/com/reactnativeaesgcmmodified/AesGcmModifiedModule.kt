package com.reactnativeaesgcmmodified

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import java.security.GeneralSecurityException
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

@ReactModule(name = "AesGcmModified")
class AesGcmModifiedModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    val GCM_TAG_LENGTH = 16

    override fun getName(): String {
        return "AesGcmModified"
    }

    private fun getSecretKeyFromString(key: ByteArray): SecretKey {
      return SecretKeySpec(key, 0, key.size, "AES")
    }

    @Throws(javax.crypto.AEADBadTagException::class)
    fun decryptData(ciphertext: ByteArray, key: ByteArray): ByteArray {
      val secretKey: SecretKey = getSecretKeyFromString(key)
      val ivData = ciphertext.slice(0..11)
      val cipherData = ciphertext.slice(12..ciphertext.size-1);
      val cipher = Cipher.getInstance("AES/GCM/NoPadding")
      val spec = GCMParameterSpec(GCM_TAG_LENGTH * 8, ivData.toByteArray())
      cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
      return cipher.doFinal(cipherData.toByteArray())
    }

    fun encryptData(plainData: ByteArray, key: ByteArray): ByteArray {
      val secretKey: SecretKey = getSecretKeyFromString(key)
      val cipher = Cipher.getInstance("AES/GCM/NoPadding")
      cipher.init(Cipher.ENCRYPT_MODE, secretKey)
      val iv = cipher.iv.copyOf()
      val result = cipher.doFinal(plainData)
      return Base64.getEncoder().encode(iv + result);
    }

    @ReactMethod
    fun decrypt(base64CipherText: String,
                key: String,
                promise: Promise) {
      try {
        val keyData = Base64.getDecoder().decode(key)
        val ciphertext: ByteArray = Base64.getDecoder().decode(base64CipherText)
        val unsealed: ByteArray = decryptData(ciphertext, keyData)

        promise.resolve(String(unsealed))
      } catch (e: javax.crypto.AEADBadTagException) {
        promise.reject("DecryptionError", "Bad auth tag exception", e)
      } catch (e: GeneralSecurityException) {
        promise.reject("DecryptionError", "Failed to decrypt", e)
      } catch (e: Exception) {
        promise.reject("DecryptionError", "Unexpected error", e)
      }
    }

    @ReactMethod
    fun encrypt(plainText: String,
                inBinary: Boolean,
                key: String,
                promise: Promise) {
      try {
        val keyData = Base64.getDecoder().decode(key)
        val plainData = if (inBinary) Base64.getDecoder().decode(plainText) else plainText.toByteArray(Charsets.UTF_8)
        val sealed = encryptData(plainData, keyData)
        promise.resolve(String(sealed))
      } catch (e: GeneralSecurityException) {
        promise.reject("EncryptionError", "Failed to encrypt", e)
      } catch (e: Exception) {
        promise.reject("EncryptionError", "Unexpected error", e)
      }
    }
}
