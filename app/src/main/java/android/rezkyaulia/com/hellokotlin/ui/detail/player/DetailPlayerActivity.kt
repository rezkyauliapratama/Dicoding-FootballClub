package android.rezkyaulia.com.hellokotlin.ui.detail.player

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailPlayerBinding
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.view.Menu
import android.view.MenuItem
import android.view.View
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

        id = intent.getStringExtra("id")

        viewModel.retrieveData(id)

        initObserver()

    }

    private fun initObserver() {
        viewModel.nameLD.observe(this, Observer {
            supportActionBar?.title = it

        })
        viewModel.uiStatusLD.observe(this, Observer {
            when (it) {
                UiStatus.HIDE_LOADER -> layoutProgress.visibility = View.GONE
                UiStatus.SHOW_LOADER -> layoutProgress.visibility = View.VISIBLE

                else -> {
                    error { "status cannot found" }
                }
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}