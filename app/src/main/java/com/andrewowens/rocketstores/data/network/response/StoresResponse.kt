package com.andrewowens.rocketstores.data.network.response

import com.andrewowens.rocketstores.data.db.entity.StoreEntry
import com.google.gson.annotations.SerializedName

data class StoresResponse(
    @SerializedName("stores")
    val storeEntries: List<StoreEntry>
)