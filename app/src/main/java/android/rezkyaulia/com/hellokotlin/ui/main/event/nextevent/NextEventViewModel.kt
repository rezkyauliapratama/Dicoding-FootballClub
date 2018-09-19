package android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.Util.AppSchedulerProvider
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 20/8/18.
 */
class NextEventViewModel @Inject constructor(private val networkApi: NetworkApi) : BaseViewModel(){
    val eventResponseLD: MutableLiveData<EventResponse> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    fun retrieveData(s: String) {
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(networkApi
                .getEventNextLeague(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    eventResponseLD.value = response
                    uiStatusLD.value = UiStatus.HIDE_LOADER
                }) { throwable ->
                    uiStatusLD.value = UiStatus.HIDE_LOADER
                })

    }
}