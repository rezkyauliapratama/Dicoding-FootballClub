package android.rezkyaulia.com.hellokotlin.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.rezkyaulia.com.hellokotlin.main.MainViewModel
import android.rezkyaulia.com.hellokotlin.main.MainViewModel_Factory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */
@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel : MainViewModel) : ViewModel



}