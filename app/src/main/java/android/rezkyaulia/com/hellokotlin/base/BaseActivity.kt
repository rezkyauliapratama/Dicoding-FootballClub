package android.rezkyaulia.com.hellokotlin.base

import android.app.Fragment
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BaseApplication
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.di.activity.ActivityComponent
import android.rezkyaulia.com.hellokotlin.di.activity.ActivityContext
import android.rezkyaulia.com.hellokotlin.di.activity.ActivityModule
import android.rezkyaulia.com.hellokotlin.di.activity.DaggerActivityComponent
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelFactory
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.telecom.Call
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */

abstract class BaseActivity<T : ViewDataBinding, V: ViewModel> :  AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dataManager: DataManager

    lateinit var viewDataBinding: T
    var viewModel : V? = null

    @Inject
    @ActivityContext
    lateinit var context: ActivityContext

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun initViewModel(): V

    abstract fun initBindingVariable() : Int

    abstract fun inject()

    var activityComponent: ActivityComponent? = null

    fun initActivityComponent(): ActivityComponent? {
        if (activityComponent == null){
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(ActivityModule(this))
                    .applicationComponent(BaseApplication.component)
                    .build()
        }
        return activityComponent
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()

        super.onCreate(savedInstanceState)

        performDataBinding()



    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())
        this.viewModel = if (viewModel == null) initViewModel() else viewModel

        viewDataBinding.setVariable(initBindingVariable(), viewModel)
        viewDataBinding.executePendingBindings()

    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
    }
}