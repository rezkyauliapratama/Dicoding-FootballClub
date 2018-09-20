package android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 20/8/18.
 */
class NextEventViewModel @Inject constructor(private val networkApi: NetworkApi) : BaseViewModel(){
    val eventResponseLD: MutableLiveData<List<Event>> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    private val subject = PublishSubject.create<String>()
    private var query = ""
    private var eventId = ""

    fun retrieveData(s: String) {
        eventId = s
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(networkApi
                .getEventNextLeague(s)
                .flatMap{t ->
                    Single.just(t.events)
                }
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