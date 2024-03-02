package com.example.memeapp


import com.google.gson.annotations.SerializedName

data class MemeData(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("success")
    val success: Boolean
)