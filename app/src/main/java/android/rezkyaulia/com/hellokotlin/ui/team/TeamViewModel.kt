package android.rezkyaulia.com.hellokotlin.ui.team

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

class TeamViewModel @Inject constructor(val networkApi: NetworkApi) : BaseViewModel(){

    val teamsLD : MutableLiveData<List<Team>> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    fun retrieveData(league: String) {
        uiStatusLD.value = UiStatus.SHOW_LOADER

        compositeDisposable.add(networkApi
                .getSearchAllTeams(league).subscribeOn(Schedulers.io())
                .flatMap{t ->
                   Single.just(t.teams)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    error { "response : "+ Gson().toJson(response) }

                    if (response != null){
                        teamsLD.value = response
                    }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }))

    }

}