package android.rezkyaulia.com.hellokotlin.ui.detail.team

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.databinding.ActivityDetailBinding
import android.rezkyaulia.com.hellokotlin.ui.detail.player.DetailPlayerActivity
import android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment.DetailTeamFragment
import android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment.DetailTeamPlayerFragment
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.LinearLayout
import com.app.infideap.stylishwidget.view.ATextView
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.content_activity_detail_team.view.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.error
import org.jetbrains.anko.startActivity

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

    lateinit var fragments: MutableList<Fragment>
    lateinit var fragment: Fragment

    private lateinit var tabAdapter: LfPagerAdapter


    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""

        id = intent.getStringExtra("id")
        error { "id $id" }

        fragments = mutableListOf()


        initTab()
        initViewPager()

        viewModel.retrieveData(id)

        initObserver()


    }

    private fun initObserver() {
        viewModel.playerIdLD.observe(this, Observer {
            ctx.startActivity<DetailPlayerActivity>("id".to("${it}"))

        })
    }

    /*init view pager*/
    private fun initTab() {
        val tabs = arrayOf(
                content_layout.tabLayout.newTab().setText("Team"),
                content_layout.tabLayout.newTab().setText("Player")
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



            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initViewPager() {
        fragments.add(DetailTeamFragment())
        fragments.add(DetailTeamPlayerFragment())

        fragment = fragments[0]
        this.tabAdapter = LfPagerAdapter(supportFragmentManager, fragments)

        content_layout.viewPager.offscreenPageLimit = 2
        content_layout.viewPager.adapter = tabAdapter
        content_layout.viewPager.isPagingEnabled = false
        content_layout.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(content_layout.tabLayout))
    }

    class LfPagerAdapter (fm: FragmentManager, private val fragments:MutableList<Fragment>): FragmentStatePagerAdapter(fm)
    {

        private val NUMITEMS = 2



        override fun getCount(): Int {
            return NUMITEMS
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> fragments[1]
                else -> fragments[0]
            }
        }


    }
}