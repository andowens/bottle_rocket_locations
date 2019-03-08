package com.andrewowens.rocketstores.data.response

import com.google.gson.annotations.SerializedName

data class StoresResponse(
    @SerializedName("stores")
    val storeEntries: List<StoreEntry>
)