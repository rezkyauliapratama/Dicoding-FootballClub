package android.rezkyaulia.com.hellokotlin.ui.main.event

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.error
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 18/9/18.
 */
class EventViewModel @Inject constructor(networkApi: NetworkApi) : BaseViewModel(){


    val eventResponseLD: MutableLiveData<List<Event>> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    private val subject = PublishSubject.create<String>()
    private var query = ""


    init {
        compositeDisposable.add(
                subject.
                        debounce(300, TimeUnit.MILLISECONDS)
                        .filter(Predicate { it: String ->
                            return@Predicate it.isNotEmpty()
                        })
                        .distinctUntilChanged()
                        .switchMap(Function<String, ObservableSource<EventResponse>> { it ->
                            return@Function networkApi.searchEventByName(it).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())

                        })
                        .subscribe({ response ->
                            error { "response : $response" }
                            if (response != null){
                                eventResponseLD.value = response.events
                            }else{
                                uiStatusLD.value = UiStatus.EMPTY
                            }

                        }, { throwable ->

                            error { "error : "+ Gson().toJson(throwable) }
                        })

        )
    }

    fun search(s: String) {
        query = s
        if (s.isEmpty()){
            uiStatusLD.value = UiStatus.HIDE_RESULT
        }else{
            uiStatusLD.value = UiStatus.SHOW_RESULT
            subject.onNext(s)
        }

    }
}