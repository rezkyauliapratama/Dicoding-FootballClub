package android.rezkyaulia.com.hellokotlin.di.presenter

import android.app.Activity
import android.content.Context
import android.rezkyaulia.com.hellokotlin.base.BaseView
import android.view.View
import dagger.Module
import dagger.Provides

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
@Module
class ContextModule(private val view: BaseView){

    @Provides
    @ActivityContext
    fun provideContext() : Context{
        return view.getContext()
    }

}