package android.rezkyaulia.com.hellokotlin.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.Util.TimeUtility
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.error
import java.util.*
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(){

    @Inject
    lateinit var timeUtils: TimeUtility

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

    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        supportActionBar?.title = getString(R.string.match_detail)

        event = intent.getParcelableExtra("event")


        viewModel.setupEvent(event)
        viewDataBinding.setVariable(BR.event,event)
        viewDataBinding.executePendingBindings()

        initObserver()

    }

    private fun initObserver() {
        viewModel.strAwayBdgLD.observe(this, Observer {
            Picasso.get().load(it).into(iv_awayTeam)
        })

        viewModel.strHomeBdgLD.observe(this, Observer {
            Picasso.get().load(it).into(iv_homeTeam)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}