package com.talhaoz.martianstalker.api

import com.talhaoz.martianstalker.model.MissionDetailsModel
import com.talhaoz.martianstalker.model.Photo
import com.talhaoz.martianstalker.util.Constants.Companion.API_KEY
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {


    @GET("rovers/curiosity/photos/")
    fun getCuriosityMissionDetails(
        @Query("sol")
        sol : Int,
        @Query("camera")
        camera : String?,
        @Query("page")
        page: Int?,
        @Query("api_Key")
        api_Key: String
    ): Single<MissionDetailsModel>


    @GET("rovers/opportunity/photos")
    fun getOpportunityMissionDetails(
        @Query("sol")
        sol : Int,
        @Query("camera")
        camera : String?,
        @Query("page")
        page: Int?,
        @Query("api_Key")
        api_Key: String
    ): Single<MissionDetailsModel>

    @GET("rovers/spirit/photos")
    fun getSpiritMissionDetails(
        @Query("sol")
        sol : Int,
        @Query("camera")
        camera : String?,
        @Query("page")
        page: Int?,
        @Query("api_Key")
        api_Key: String
    ): Single<MissionDetailsModel>


}