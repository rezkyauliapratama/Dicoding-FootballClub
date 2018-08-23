package android.rezkyaulia.com.hellokotlin.ui.detail

import android.arch.lifecycle.ViewModelProviders
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun initViewModel(): DetailViewModel {
       return  ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }

}