fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    return EncryptedSharedPreferences.create(
        context,
        "encrypted_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

// Usage
val encryptedPrefs = getEncryptedSharedPreferences(context)
encryptedPrefs.edit().putString("secure_key", "secure_value").apply()
val value = encryptedPrefs.getString("secure_key", null)
println(value) // Output: secure_value
