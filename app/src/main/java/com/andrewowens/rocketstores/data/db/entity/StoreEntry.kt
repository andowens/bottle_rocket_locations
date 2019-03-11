package com.andrewowens.rocketstores.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "stores", indices = [Index(value = ["storeID"], unique = true)])
data class StoreEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
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