package com.talhaoz.martianstalker.model

import com.google.gson.annotations.SerializedName

data class MissionDetailsModel(
    @SerializedName("photos")
    val photos: ArrayList<Photo>
)