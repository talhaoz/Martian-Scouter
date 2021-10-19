package com.talhaoz.martianstalker.model

import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("id")
    val id: Int,
    @SerializedName("landing_date")
    val landing_date: String,
    @SerializedName("launch_date")
    val launch_date: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String
)