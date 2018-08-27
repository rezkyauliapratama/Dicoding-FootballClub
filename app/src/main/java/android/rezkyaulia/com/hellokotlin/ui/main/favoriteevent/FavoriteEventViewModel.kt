package android.rezkyaulia.com.hellokotlin.ui.main.favoriteevent

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 26/8/18.
 */
class FavoriteEventViewModel @Inject constructor(private val dataManager: DataManager) : BaseViewModel(){

    val favEventResponseLD: MutableLiveData<List<FavoriteEvent>> = MutableLiveData()
    val uiStatusLD : MutableLiveData<UiStatus> = MutableLiveData()

    fun retrieveData() {
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(dataManager.db.manageFavoriteEvent
                .loadAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null){
                        favEventResponseLD.value = response
                    }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER


                }))

    }
}