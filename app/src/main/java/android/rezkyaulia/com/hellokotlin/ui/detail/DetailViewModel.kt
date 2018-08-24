package android.rezkyaulia.com.hellokotlin.ui.detail

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.util.TimeUtils
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

class DetailViewModel @Inject constructor(val dataManager: DataManager): BaseViewModel(){

    val strHomeBdgLD : MutableLiveData<String> = MutableLiveData()
    val strAwayBdgLD : MutableLiveData<String> = MutableLiveData()

    fun setupEvent(event: Event?) {

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


}