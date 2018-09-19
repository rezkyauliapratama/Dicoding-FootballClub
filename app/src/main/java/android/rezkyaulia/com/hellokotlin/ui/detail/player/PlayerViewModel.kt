package android.rezkyaulia.com.hellokotlin.ui.detail.player

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableArrayMap
import android.databinding.ObservableField
import android.databinding.ObservableParcelable
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.model.Player
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 19/9/18.
 */
class PlayerViewModel @Inject constructor(val networkApi: NetworkApi): BaseViewModel(){

    companion object {
        const val IMAGE = "image"
        const val WEIGHT = "weight"
        const val HEIGHT = "height"
        const val POSITION = "positon"
        const val DESCRIPTION = "description"
    }


    var items: ObservableArrayMap<String, String> = ObservableArrayMap()

    val uiStatusLD: MutableLiveData<UiStatus> = MutableLiveData()
    val nameLD: MutableLiveData<String> = MutableLiveData()

    fun retrieveData(playerID : String){
        uiStatusLD.value = UiStatus.SHOW_LOADER
        compositeDisposable.add(networkApi
                .getDetailPlayer(playerID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    error { "response : "+ Gson().toJson(response) }

                    if (!response.player.isEmpty()){
                        items.put(IMAGE,response.player[0].strFanart1)
                        items.put(WEIGHT,response.player[0].strWeight)
                        items.put(HEIGHT,response.player[0].strHeight)
                        items.put(POSITION,response.player[0].strPosition)
                        items.put(DESCRIPTION,response.player[0].strDescriptionEN)

                        nameLD.value = response.player[0].strPlayer
                    }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }, { throwable ->
                    error { "error : "+ Gson().toJson(throwable) }
                    uiStatusLD.value = UiStatus.HIDE_LOADER

                }))
    }


}