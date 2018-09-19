package android.rezkyaulia.com.hellokotlin.ui.detail.player

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailPlayerBinding
import kotlinx.android.synthetic.main.activity_detail_player.*
import org.jetbrains.anko.error

/**
 * Created by Rezky Aulia Pratama on 19/9/18.
 */
class DetailPlayerActivity : BaseActivity<ActivityDetailPlayerBinding, PlayerViewModel>(){

    lateinit var id:String

    override fun getLayoutId(): Int {
        return R.layout.activity_detail_player
    }

    override fun initViewModel(): PlayerViewModel {
        return ViewModelProviders.of(this,viewModelFactory).get(PlayerViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""

        id = intent.getStringExtra("id")
        error { "id $id" }

        viewModel.retrieveData(id)

    }

}