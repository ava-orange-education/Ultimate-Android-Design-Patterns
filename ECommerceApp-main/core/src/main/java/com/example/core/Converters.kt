package com.example.core

import androidx.room.TypeConverter
import com.example.core.db.CartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromCartItemList(cartItems: List<CartItem>?): String {
        return Gson().toJson(cartItems)
    }

    @TypeConverter
    fun toCartItemList(cartItemsString: String): List<CartItem>? {
        val listType = object : TypeToken<List<CartItem>>() {}.type
        return Gson().fromJson(cartItemsString, listType)
    }
}