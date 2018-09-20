package android.rezkyaulia.com.hellokotlin.ui.main.team

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.data.model.TeamResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.Observable
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

class TeamViewModel @Inject constructor(val networkApi: NetworkApi) : BaseViewModel(){

    val teamsLD : MutableLiveData<List<Team>> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    val subject = PublishSubject.create<String>()
    var query = ""
    var league = ""

    init {
        compositeDisposable.add(
                subject.
                        debounce(300, TimeUnit.MILLISECONDS)
                        .filter(Predicate { it: String ->
                            return@Predicate it.isNotEmpty()
                        })
                        .distinctUntilChanged()
                        .switchMap(Function<String, ObservableSource<TeamResponse>> { it ->
                                return@Function networkApi.searchTeamByName(it).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())

                        })
                        .subscribe({ response ->
                            if (response != null){
                                teamsLD.value = response.teams
                            }else{
                                uiStatusLD.value = UiStatus.EMPTY
                            }
                            uiStatusLD.value = UiStatus.HIDE_LOADER

                        }, { throwable ->
                            uiStatusLD.value = UiStatus.HIDE_LOADER

                            error { "error : "+ Gson().toJson(throwable) }
                        })

        )
    }
    fun retrieveData(league: String) {
        this.league = league
        uiStatusLD.value = UiStatus.SHOW_LOADER

        compositeDisposable.add(networkApi
                .getSearchAllTeams(league).subscribeOn(Schedulers.io())
                .flatMap{t ->
                   Single.just(t.teams)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (response != null){
                        teamsLD.value = response
                    }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }))

    }

    fun search(s: String) {
        query = s
        if (s.isEmpty()){
            retrieveData(this.league)
        }else{
            uiStatusLD.value = UiStatus.SHOW_LOADER
            subject.onNext(s)
        }

    }


}