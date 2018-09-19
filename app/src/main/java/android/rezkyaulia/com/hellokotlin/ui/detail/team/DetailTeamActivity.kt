package android.rezkyaulia.com.hellokotlin.ui.detail.team

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.error

class DetailTeamActivity : BaseActivity<ActivityDetailBinding,DetailTeamPlayerViewModel>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_detail_team
    }

    override fun initViewModel(): DetailTeamPlayerViewModel {
        return ViewModelProviders.of(this,viewModelFactory).get(DetailTeamPlayerViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }

    lateinit var id: String

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