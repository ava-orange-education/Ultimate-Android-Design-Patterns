// Configure Tink
AeadConfig.register()

// Generate a key and obtain an AEAD instance
val keysetHandle = KeysetHandle.generateNew(AeadConfig.AES256_GCM)
val aead = keysetHandle.getPrimitive(Aead::class.java)

// Encrypt the data
val plaintext = "Sensitive data".toByteArray()
val associatedData = "Metadata".toByteArray()
val ciphertext = aead.encrypt(plaintext, associatedData)

// Decrypt the data
val decryptedText = aead.decrypt(ciphertext, associatedData)
println(String(decryptedText)) // Output: Sensitive data
