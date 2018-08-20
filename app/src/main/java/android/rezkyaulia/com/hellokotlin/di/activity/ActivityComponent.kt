package android.rezkyaulia.com.hellokotlin.di.activity

import android.rezkyaulia.com.hellokotlin.di.application.ApplicationComponent
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelFactory
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelModule
import android.rezkyaulia.com.hellokotlin.main.MainActivity
import dagger.Component

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */
@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent{

    fun inject(activity:MainActivity)
}