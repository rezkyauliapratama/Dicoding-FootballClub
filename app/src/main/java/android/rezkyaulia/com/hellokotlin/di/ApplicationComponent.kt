package android.rezkyaulia.com.hellokotlin.di

import android.rezkyaulia.com.hellokotlin.BaseApplication
import android.rezkyaulia.com.hellokotlin.data.DataManager
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Singleton
@Component(modules = [ApplicationModule::class,NetworkModule::class])
interface ApplicationComponent{

    fun inject(app : BaseApplication)


    fun getDataManager(): DataManager

}