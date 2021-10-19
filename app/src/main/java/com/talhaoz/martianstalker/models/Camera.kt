package com.talhaoz.martianstalker.model

import com.google.gson.annotations.SerializedName

data class Camera(
    @SerializedName("full_name")
    val full_name: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("rover_id")
    val rover_id: Int
)