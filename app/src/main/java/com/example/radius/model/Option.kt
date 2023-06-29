package com.example.radius.model


import com.google.gson.annotations.SerializedName

data class Option(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)