package com.example.core

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val loremIpsum = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, " +
        "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum " +
        "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium " +
        "optio, eaque rerum! Provident similique accusantium nemo autem."

fun Long.formatTimestamp(): String {
    val sdf = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

fun String.hash(): String {
    val bytes = this.toString().toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}