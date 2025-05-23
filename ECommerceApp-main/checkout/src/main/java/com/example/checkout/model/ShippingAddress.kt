package com.example.checkout.model

data class ShippingAddress(
    var fullName: String,
    var phoneNumber: String,
    var streetAddress: String,
    var postalCodeOrZipCode: String,
    var city: String,
    var stateOrProvinceOrRegion: String,
    var country: String
)