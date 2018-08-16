package android.rezkyaulia.com.hellokotlin.base

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelFactory
import android.support.v4.app.Fragment
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 16/8/18.
 */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment(){

    @Inject
    lateinit var mViewModelFactory : ViewModelFactory


}