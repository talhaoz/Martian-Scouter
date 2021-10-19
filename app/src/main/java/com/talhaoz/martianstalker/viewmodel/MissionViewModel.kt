package com.talhaoz.martianstalker.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talhaoz.martianstalker.api.RetrofitClient
import com.talhaoz.martianstalker.model.MissionDetailsModel
import com.talhaoz.martianstalker.model.Photo
import com.talhaoz.martianstalker.util.Constants.Companion.API_KEY
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MissionViewModel : ViewModel() {

    var compositeDisposable: CompositeDisposable

    var missionList: MutableLiveData<ArrayList<Photo>>
    //var missionListBackUp : ArrayList<Photo>? = null
    val dataError = MutableLiveData<Boolean>()
    val dataLoading = MutableLiveData<Boolean>()


    init {
        compositeDisposable = CompositeDisposable()
        missionList = MutableLiveData()

    }

    //var pageParam  : Int? = 1
    var page=1
    var isListOver = false
    fun getPhotos(roverNameP: String,cameraP : String?,isRefresh : Boolean) : Boolean {

        if(isRefresh)
        {
            page=1
            isListOver=false
        }

        val retrofitRes = RetrofitClient.loadData()

        if(!isListOver)
        {
            lateinit var singleObj : Single<MissionDetailsModel>

            if(roverNameP.equals("curiosity"))
                singleObj = retrofitRes.getCuriosityMissionDetails(sol=1000,camera=cameraP,page=page,api_Key = API_KEY)
            else if(roverNameP.equals("opportunity"))
                singleObj = retrofitRes.getOpportunityMissionDetails(sol=1000,camera=cameraP,page=page,api_Key = API_KEY)
            else if(roverNameP.equals("spirit"))
                singleObj = retrofitRes.getSpiritMissionDetails(sol=1000,camera=cameraP,page=page,api_Key = API_KEY)

            getDataFromApi(singleObj)
        }
        return isListOver
    }


    private fun getDataFromApi(singleObject : Single<MissionDetailsModel>){
        dataLoading.value= true

        compositeDisposable.add(
            singleObject
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MissionDetailsModel>(){
                    override fun onSuccess( it : MissionDetailsModel) {

                                if (it.photos.size < 25)
                                    isListOver=true

                                page++ // increase page number to get next page on scroll

                                missionList.value = it.photos
                                dataError.value = false
                                dataLoading.value = false

                    }

                    override fun onError(e: Throwable) {
                        dataLoading.value=false
                        dataError.value=true
                        isListOver=true
                        println("ERRORRRRRRRRRRRRRRRRRRR while Loading Photos")
                        e.printStackTrace()

                    }

                })
        )

    }



}