package com.talhaoz.martianstalker.api

import android.provider.Contacts
import com.talhaoz.martianstalker.model.MissionDetailsModel
import com.talhaoz.martianstalker.model.Photo
import com.talhaoz.martianstalker.util.Constants.Companion.BASE_URL
import com.talhaoz.martianstalker.util.Constants.Companion.CONNECTION_TIMEOUT
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        fun loadData() : RetrofitInterface {

            return Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RetrofitInterface::class.java)

        }


        private fun getHttpClient() : OkHttpClient
        {
            val httpClient = OkHttpClient.Builder()

            httpClient.readTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            httpClient.connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            httpClient.writeTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)

            return httpClient.build()
        }


    }
}