package android.rezkyaulia.com.hellokotlin.base

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
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 15/8/18.
 */
abstract class BaseActivity<T : ViewDataBinding, V: ViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dataManager: DataManager

    lateinit var mViewDataBinding: T
    var mViewModel : V? = null

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
    abstract fun getViewModel(): V

    abstract fun getBindingVariable() : Int

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
        mViewDataBinding = DataBindingUtil.setContentView(this,getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel

        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()

    }
}