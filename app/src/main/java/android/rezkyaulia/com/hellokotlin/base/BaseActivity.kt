package android.rezkyaulia.com.hellokotlin.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Rezky Aulia Pratama on 6/8/18.
 */
abstract class BaseActivity<P: BasePresenter<BaseView>> : BaseView, AppCompatActivity(){

    protected lateinit var presenter:P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
    }

    protected abstract fun initPresenter(): P

    override fun getContext(): Context {
        return this
    }

}