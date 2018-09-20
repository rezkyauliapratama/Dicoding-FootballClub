package android.rezkyaulia.com.hellokotlin.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.databinding.ActivityMainBinding
import android.rezkyaulia.com.hellokotlin.ui.detail.event.DetailActivity
import android.rezkyaulia.com.hellokotlin.ui.detail.team.DetailTeamActivity
import android.rezkyaulia.com.hellokotlin.ui.main.event.EventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.favoriteevent.FavoriteEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.favorite.FavoriteFragment
import android.rezkyaulia.com.hellokotlin.ui.main.team.TeamFragment
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Toast
import com.app.infideap.stylishwidget.view.ATextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_main.view.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import android.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import android.support.v4.view.MenuItemCompat.getActionView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import android.view.MenuItem
import android.view.View
import org.jetbrains.anko.error


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    var mSearchView : SearchView ? = null
    var menu : Menu ?= null

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModel() = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)



    override fun initBindingVariable() = BR.viewModel


    override fun inject() {
        initActivityComponent()?.inject(this)

    }

    lateinit var fragments: MutableList<Fragment>
    lateinit var fragment: Fragment


    private lateinit var tabAdapter: LfPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        fragments = mutableListOf()
        initTab()
        initViewPager()
        initObserver()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        this.menu = menu
        val mSearch = menu.findItem(R.id.action_search)

        mSearchView = mSearch.getActionView() as SearchView
        mSearchView?.setQueryHint("Search")

        mSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                error { "qeury : $newText" }
                if (fragment is TeamFragment){
                    viewModel.teamSearchQueryLD.value = newText
                }else if (fragment is EventFragment){
                    viewModel.eventSearchQueryLD.value = newText
                }
                return true
            }
        })

        mSearchView?.setOnSearchClickListener(View.OnClickListener {
            //perform your click operation here
            if (fragment is EventFragment){


            }
        })


        return super.onCreateOptionsMenu(menu)
    }


    private fun initObserver() {
        viewModel.eventIdLD.observe(this, Observer {
            ctx.startActivity<DetailActivity>("id".to("${it}"))

        })

        viewModel.teamIdLD.observe(this, Observer { t ->
            ctx.startActivity<DetailTeamActivity>("id".to("${t}"))

        })
    }


    /*init view pager*/
    private fun initTab() {
        val tabs = arrayOf(
                content_layout.tabLayout.newTab().setText("Match"),
                content_layout.tabLayout.newTab().setText("Team"),
                content_layout.tabLayout.newTab().setText("Favorite")
        )

        for (tab in tabs) {
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layout.weightSum = 1f
            val newTab = ATextView(this)
            newTab.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            newTab.gravity = Gravity.CENTER
            newTab.maxLines = 1
            newTab.text = tab.text

            newTab.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))

            layout.addView(newTab)

            tab.customView = layout
            content_layout.tabLayout.addTab(tab)
        }

        content_layout.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                fragment = fragments[tab.position]
                content_layout.viewPager.currentItem = tab.position

                mSearchView?.apply { setQuery("", false) }
           /*     if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }*/

                if (fragment is FavoriteFragment){
                    menu?.findItem(R.id.action_search)?.isVisible = false
                    mSearchView?.visibility  = View.GONE


                }else if (fragment is TeamFragment){
                    menu?.findItem(R.id.action_search)?.isVisible = true
                    mSearchView?.visibility  = View.VISIBLE

                    viewModel.teamSearchQueryLD.value = ""

                }else if (fragment is EventFragment){
                    menu?.findItem(R.id.action_search)?.isVisible = true
                    menu?.findItem(R.id.action_search)?.collapseActionView()
                    mSearchView?.visibility  = View.VISIBLE
                    viewModel.eventSearchQueryLD.value = ""
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initViewPager() {
        fragments.add(EventFragment.newInstance())
        fragments.add(TeamFragment.newInstance())
        fragments.add(FavoriteFragment.newInstance())

        fragment = fragments[0]
        this.tabAdapter = LfPagerAdapter(supportFragmentManager, fragments)

        content_layout.viewPager.offscreenPageLimit = 3
        content_layout.viewPager.adapter = tabAdapter
        content_layout.viewPager.isPagingEnabled = false
        content_layout.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(content_layout.tabLayout))
    }

    class LfPagerAdapter (fm: FragmentManager, private val fragments:MutableList<Fragment>): FragmentStatePagerAdapter(fm)
    {

        private val NUMITEMS = 3



        override fun getCount(): Int {
            return NUMITEMS
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> fragments[1]
                2 -> fragments[2]
                else -> fragments[0]
            }
        }


    }
}

