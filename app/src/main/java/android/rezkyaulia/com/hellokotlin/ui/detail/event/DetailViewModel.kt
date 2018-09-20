package android.rezkyaulia.com.hellokotlin.ui.detail.event

import android.arch.lifecycle.MutableLiveData
import android.database.sqlite.SQLiteConstraintException
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val dataManager: DataManager): BaseViewModel(){

    val eventLD : MutableLiveData<Event> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()
    val strHomeBdgLD : MutableLiveData<String> = MutableLiveData()
    val strAwayBdgLD : MutableLiveData<String> = MutableLiveData()
    val boolFavoriteLD : MutableLiveData<Boolean> = MutableLiveData()


    fun retrieveEvent(id : String){
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(dataManager.networkApi
                .getSpecificEvent(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null && response.events.isNotEmpty()) eventLD.value = response.events.get(0)
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }))

        favoriteState(id)
    }

    fun setupImage(event: Event?) {

        error {"setupEvent"}
        compositeDisposable.add(dataManager.networkApi
                .getSpecificTeam(event?.idHomeTeam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    error { Gson().toJson(response) }
                    if (response != null && response.teams.isNotEmpty()) strHomeBdgLD.value = response.teams[0].teamBadge


                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }

                }))

        compositeDisposable.add(dataManager.networkApi
                .getSpecificTeam(event?.idAwayTeam).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
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


    private fun favoriteState(id : String){
        compositeDisposable.add(dataManager.db.manageFavoriteEvent.loadByEventId(id).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                doOnNext { events ->
                    boolFavoriteLD.value = !events.isEmpty()
                }.
                subscribe())

    }

}