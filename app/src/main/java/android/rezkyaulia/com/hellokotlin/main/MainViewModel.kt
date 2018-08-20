package android.rezkyaulia.com.hellokotlin.main

import android.rezkyaulia.com.hellokotlin.base.BaseViewModel
import android.rezkyaulia.com.hellokotlin.data.DataManager
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 18/8/18.
 */
class MainViewModel @Inject constructor(val dataManager: DataManager) : BaseViewModel(){


    init {
        error { "init mainviewModel" }

    }

    fun retrieveData(s: String) {

    }
}