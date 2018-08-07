package android.rezkyaulia.com.hellokotlin

import android.app.Application
import android.content.Context
import android.rezkyaulia.com.hellokotlin.di.*
import com.androidnetworking.AndroidNetworking
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */

class BaseApplication : Application() {

    companion object {
        lateinit var component : ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)
        component.inject(this)

        AndroidNetworking.initialize(this)
    }

    private fun initDagger(app: BaseApplication): ApplicationComponent =
            DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(app))
                    .networkModule(NetworkModule())
                    .build()


}