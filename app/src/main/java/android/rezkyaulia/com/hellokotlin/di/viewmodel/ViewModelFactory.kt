package android.rezkyaulia.com.hellokotlin.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.NonNull
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */

@Singleton
class ViewModelFactory @Inject constructor(val viewModels: Map<Class<out ViewModel>,Provider<ViewModel>>) : ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try{
            return viewModels.get(modelClass)?.get() as T
        }catch (e : Exception){
            throw RuntimeException("Error creating view model for class : $modelClass.simpleName ",e)
        }
    }

}