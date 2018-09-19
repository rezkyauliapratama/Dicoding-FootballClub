package android.rezkyaulia.com.hellokotlin.ui.detail.team

import android.databinding.ObservableArrayMap
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import javax.inject.Inject

class DetailPlayerViewModel @Inject constructor(api: NetworkApi): BaseViewModel(){

    companion object {
        const val CLUB_NAME = "CLUB_NAME"
        const val CLUB_YEAR = "CLUB_YEAR"
        const val CLUB_STADIUM = "CLUB_STADIUM"
        const val CLUB_DETAIL = "CLUB_DETAIL"
    }

    var items: ObservableArrayMap<String, String> = ObservableArrayMap()

}