package android.rezkyaulia.com.hellokotlin.ui.main

import android.arch.lifecycle.MutableLiveData
import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 18/8/18.
 */
class MainViewModel @Inject constructor(val dataManager: DataManager) : BaseViewModel(){


    val eventIdLD : MutableLiveData<String> = MutableLiveData()
    val teamIdLD : MutableLiveData<String> = MutableLiveData()

}