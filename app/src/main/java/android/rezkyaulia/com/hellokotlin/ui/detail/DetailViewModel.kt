package android.rezkyaulia.com.hellokotlin.ui.detail

import android.arch.lifecycle.MutableLiveData
import android.database.sqlite.SQLiteConstraintException
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.util.TimeUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.error
import javax.inject.Inject

class DetailViewModel @Inject constructor(val dataManager: DataManager): BaseViewModel(){

    val eventLD : MutableLiveData<Event> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()
    val strHomeBdgLD : MutableLiveData<String> = MutableLiveData()
    val strAwayBdgLD : MutableLiveData<String> = MutableLiveData()


    fun retrieveEvent(id : String){
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(dataManager.getRepo().eventApi
                .eventSpecific(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    error { Gson().toJson(response) }
                    if (response != null && response.events.size > 0) eventLD.value = response.events.get(0)
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }))
    }

    fun setupImage(event: Event?) {

        error {"setupEvent"}
        compositeDisposable.add(dataManager.getRepo().teamApi
                .teamById(event?.idHomeTeam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    error { Gson().toJson(response) }
                    if (response != null && response.teams.size > 0) strHomeBdgLD.value = response.teams[0].teamBadge


                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }

                }))

        compositeDisposable.add(dataManager.getRepo().teamApi
                .teamById(event?.idAwayTeam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    error { Gson().toJson(response) }
                    if (response != null) strAwayBdgLD.value = response.teams[0].teamBadge

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }

                }))


    }

    fun addToFavorite(event: Event){
        try {
            val b = dataManager.db.manageFavoriteEvent.insert(event)
            if (b)
                uiStatusLD.value = UiStatus.FAVORITE_ADD
        } catch (e: SQLiteConstraintException){
        }
    }

    fun removeFromFavorite(id : String){
        compositeDisposable.add(
                dataManager.db.manageFavoriteEvent.delete(id).
                        subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        doOnNext { it ->
                            if (it){
                                uiStatusLD.value = UiStatus.FAVORITE_REMOVE
                            }else{
                                uiStatusLD.value = UiStatus.FAVORITE_NOT_REMOVE

                            }
                        }.
                        subscribe())
    }


}