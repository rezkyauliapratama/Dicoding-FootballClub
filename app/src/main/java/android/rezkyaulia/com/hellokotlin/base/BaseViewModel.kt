package android.rezkyaulia.com.hellokotlin.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */
abstract class BaseViewModel(val compositeDisposable: CompositeDisposable) : ViewModel() {


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}