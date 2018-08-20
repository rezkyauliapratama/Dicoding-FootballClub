package android.rezkyaulia.com.hellokotlin.di.application

import android.rezkyaulia.com.hellokotlin.BaseApplication
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelFactory
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, ViewModelModule::class])
interface ApplicationComponent{

    fun inject(app : BaseApplication)

    fun getDataManager(): DataManager
    fun getViewModelFactory() : ViewModelFactory
}