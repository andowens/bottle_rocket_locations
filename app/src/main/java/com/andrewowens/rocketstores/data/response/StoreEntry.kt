package com.andrewowens.rocketstores.data.response

data class StoreEntry(
    val address: String,
    val city: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val phone: String,
    val state: String,
    val storeID: String,
    val storeLogoURL: String,
    val zipcode: String
)