package android.rezkyaulia.com.hellokotlin.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.R.array.league
import android.rezkyaulia.com.hellokotlin.R.array.league_id
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.data.model.Team
import android.rezkyaulia.com.hellokotlin.databinding.ActivityMainBinding
import android.rezkyaulia.com.hellokotlin.main.last_event.LastEventFragment
import android.rezkyaulia.com.hellokotlin.main.next_event.NextEventFragment
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.*
import com.app.infideap.stylishwidget.view.ATextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_activity_main.view.*
import org.jetbrains.anko.*

class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {
    override fun getLayoutId() = R.layout.activity_main


    override fun initViewModel() = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)



    override fun initBindingVariable() = BR.viewModel


    override fun inject() {
        initActivityComponent()?.inject(this)
    }

    lateinit var fragments: MutableList<Fragment>
    lateinit var fragment: Fragment


    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var tabAdapter: LfPagerAdapter

    private lateinit var leagueName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val spinnerItems = resources.getStringArray(league)
        val arrLeagueId = resources.getStringArray(league_id)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        error { Gson().toJson(arrLeagueId) }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                viewModel.leagueIdLD.value = arrLeagueId[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        fragments = mutableListOf()
        initTab()
        initViewPager()
        initObserver()
    }

    private fun initObserver() {
        viewModel.eventLD.observe(this, Observer {
            //TODO add logic to start detail activity
        })
    }


    /*init view pager*/
    private fun initTab() {
        val tabs = arrayOf(content_layout.tabLayout.newTab().setText("Prev Match"), content_layout.tabLayout.newTab().setText("Next Match"))

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
                fragment = fragments.get(tab.position)
                content_layout.viewPager.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initViewPager() {
        fragments.add(LastEventFragment.newInstance())
        fragments.add(NextEventFragment.newInstance())

        fragment = fragments.get(0)
        this.tabAdapter = LfPagerAdapter(supportFragmentManager,fragments)

        content_layout.viewPager.setAdapter(tabAdapter)
        content_layout.viewPager.setPagingEnabled(false)
        content_layout.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(content_layout.tabLayout))
    }

    class LfPagerAdapter (fm: FragmentManager, private val fragments:MutableList<Fragment>): FragmentStatePagerAdapter(fm)
    {

        private val NUM_ITEMS = 2



        override fun getCount(): Int {
            return NUM_ITEMS
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                1 -> return fragments[1]
                else -> return fragments[0]
            }
        }


}}

