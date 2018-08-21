package android.rezkyaulia.com.hellokotlin.main

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import com.google.gson.Gson
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 18/8/18.
 */
class MainViewModel @Inject constructor(val dataManager: DataManager) : BaseViewModel(){

    val leagueIdLD : MutableLiveData<String> = MutableLiveData()


}