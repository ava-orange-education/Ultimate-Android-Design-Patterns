// Generate a keystore key
fun generateKey(alias: String) {
    val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
    keyGenerator.init(
        KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
    )
    keyGenerator.generateKey()
}

// Encrypt a file
fun encryptData(alias: String, data: ByteArray): Pair<ByteArray, ByteArray> {
    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null)
    val secretKey = keyStore.getKey(alias, null) as SecretKey

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)

    val iv = cipher.iv // Initialization vector
    val encryptedData = cipher.doFinal(data)

    return Pair(iv, encryptedData)
}

// Decrypt a file
fun decryptData(alias: String, iv: ByteArray, encryptedData: ByteArray): ByteArray {
    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null)
    val secretKey = keyStore.getKey(alias, null) as SecretKey

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

    return cipher.doFinal(encryptedData)
}

// Usage
val alias = "secureKeyAlias"
generateKey(alias)
val data = "Sensitive data".toByteArray()
val (iv, encryptedData) = encryptData(alias, data)
val decryptedData = decryptData(alias, iv, encryptedData)
println(String(decryptedData)) // Output: Sensitive data
