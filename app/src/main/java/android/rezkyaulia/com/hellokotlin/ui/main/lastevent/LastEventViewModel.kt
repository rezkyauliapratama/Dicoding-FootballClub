package android.rezkyaulia.com.hellokotlin.ui.main.lastevent

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.api.TheSportDBApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.android.UI
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 20/8/18.
 */
class LastEventViewModel @Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    val eventResponseLD: MutableLiveData<EventResponse> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    fun retrieveData(s: String) {
        uiStatusLD.value = UiStatus.SHOW_LOADER

        compositeDisposable.add(dataManager.api.eventApi
                .eventPastByLeagueId(s,"lastEvent").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null){
                        eventResponseLD.value = response
                    }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER


                }))

    }
}