package android.rezkyaulia.com.hellokotlin.di.presenter

import android.net.Network
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.di.ApplicationComponent
import android.rezkyaulia.com.hellokotlin.di.ApplicationModule
import android.rezkyaulia.com.hellokotlin.di.NetworkModule
import android.rezkyaulia.com.hellokotlin.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface PresenterComponent{
    fun inject(mainPresenter: MainPresenter)

    fun getDataManager():DataManager
}