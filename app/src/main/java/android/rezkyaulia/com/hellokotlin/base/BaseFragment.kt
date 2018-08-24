package android.rezkyaulia.com.hellokotlin.base

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BaseApplication
import android.rezkyaulia.com.hellokotlin.di.activity.ActivityComponent
import android.rezkyaulia.com.hellokotlin.di.activity.ActivityContext
import android.rezkyaulia.com.hellokotlin.di.activity.ActivityModule
import android.rezkyaulia.com.hellokotlin.di.activity.DaggerActivityComponent
import android.rezkyaulia.com.hellokotlin.di.viewmodel.ViewModelFactory
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 16/8/18.
 */
abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() , AnkoLogger{

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    lateinit var viewDataBinding: T
    lateinit var viewModel : V


    lateinit var activity: Activity

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
                    .activityModule(ActivityModule(activity))
                    .applicationComponent(BaseApplication.component)
                    .build()
        }
        return activityComponent
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity){
            this.activity = context
            inject()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,getLayoutId(),container,false)
        return viewDataBinding.root
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(initBindingVariable(),viewModel)
        viewDataBinding.executePendingBindings()
    }

}