package com.example.radius.model


import com.google.gson.annotations.SerializedName

data class RadiusResponse(
    @SerializedName("exclusions")
    val exclusions: List<List<Exclusion?>?>,
    @SerializedName("facilities")
    val facilities: List<Facility?>
)