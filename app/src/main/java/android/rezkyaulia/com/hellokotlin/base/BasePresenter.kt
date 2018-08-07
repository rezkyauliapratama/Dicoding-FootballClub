package android.rezkyaulia.com.hellokotlin.base

import android.rezkyaulia.com.hellokotlin.BaseApplication
import android.rezkyaulia.com.hellokotlin.data.ApiRepository
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.di.NetworkModule
import android.rezkyaulia.com.hellokotlin.di.presenter.ContextModule
import android.rezkyaulia.com.hellokotlin.di.presenter.DaggerPresenterComponent
import android.rezkyaulia.com.hellokotlin.di.presenter.PresenterComponent
import android.rezkyaulia.com.hellokotlin.main.MainPresenter
import android.util.Log
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
abstract class BasePresenter<out View: BaseView>(protected val view : View) : AnkoLogger{

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    @Inject
    lateinit var dataManager: DataManager
  /*  @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var apiRepository: ApiRepository*/

    init {
        inject()
    }

    open fun start(){

    }

    open fun stop(){
        compositeDisposable.clear()
    }


    private val injector: PresenterComponent = DaggerPresenterComponent
            .builder()
            .networkModule(NetworkModule())
            .build()

    private fun inject(){
        when(this){
            is MainPresenter -> {if (injector != null){
                Log.e("pres","injector != null")
            }}
        }
    }

}