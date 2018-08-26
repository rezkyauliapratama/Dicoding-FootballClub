package android.rezkyaulia.com.hellokotlin.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.Util.TimeUtility
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailBinding
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.error
import java.util.*
import javax.inject.Inject

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>(){

    private lateinit var id: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

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

        id = intent.getStringExtra("id")

        initObserver()
        viewModel.retrieveEvent(id)

        swipe_detail.setOnRefreshListener {
            error{ "onswipe" }
            viewModel.retrieveEvent(id)
        }

    }


    private fun initObserver() {
        viewModel.strAwayBdgLD.observe(this, Observer {
            Picasso.get().load(it).into(iv_awayTeam)
        })

        viewModel.strHomeBdgLD.observe(this, Observer {
            Picasso.get().load(it).into(iv_homeTeam)
        })

        viewModel.eventLD.observe(this, Observer {
            swipe_detail.isRefreshing = false
            event = it
            viewModel.setupImage(event)

            val date = timeUtils.convertStringToDate(event!!.dateEvent!!)
            event!!.dateEvent = date?.let { it1 -> timeUtils.getUserFriendlyDate(it1) }
            viewDataBinding.setVariable(BR.event,event)
            viewDataBinding.executePendingBindings()
        })

        viewModel.uiStatusLD.observe(this, Observer {
            if (it == UiStatus.HIDE_LOADER){
                swipe_detail.isRefreshing = false
            }else if (it == UiStatus.SHOW_LOADER){
                swipe_detail.isRefreshing = true

            }else if (it == UiStatus.FAVORITE_ADD){
                snackbar(swipe_detail,"Favorite added").show()
            }else if (it == UiStatus.FAVORITE_REMOVE){
                snackbar(swipe_detail,"Favorite removed").show()
            }else if (it == UiStatus.FAVORITE_NOT_REMOVE){
                snackbar(swipe_detail,"Favorite cannot remove").show()

            }
        })


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addToFavorite(){
        if (event != null)
            viewModel.addToFavorite(event!!)

    }

    private fun removeFromFavorite(){
        if (event != null)
            viewModel.removeFromFavorite(event!!.idEvent!!)
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState(){


    }

}