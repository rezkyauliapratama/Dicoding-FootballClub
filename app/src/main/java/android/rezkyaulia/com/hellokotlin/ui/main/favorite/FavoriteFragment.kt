package android.rezkyaulia.com.hellokotlin.ui.main.favorite

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.databinding.FragmentFavoriteBinding
import android.rezkyaulia.com.hellokotlin.ui.main.event.favoriteevent.FavoriteEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.team.favoriteteam.FavoriteTeamFragment
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.app.infideap.stylishwidget.view.ATextView
import kotlinx.android.synthetic.main.fragment_favorite.*

/**
 * Created by Rezky Aulia Pratama on 20/9/18.
 */
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding,FavoriteViewModel>(){

    private lateinit var fragments: MutableList<Fragment>
    private lateinit var fragment: Fragment
    private lateinit var tabAdapter: LfPagerAdapter

    companion object {
        fun newInstance (): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite
    }

    override fun initViewModel(): FavoriteViewModel {
        return ViewModelProviders.of(this,viewModelFactory).get(FavoriteViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragments = mutableListOf()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTab()
        initViewPager()
    }

    /*init view pager*/
    private fun initTab() {
        val tabs = arrayOf(tabLayoutFavorite.newTab().setText("Event"), tabLayoutFavorite.newTab().setText("Team"))

        for (tab in tabs) {
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            layout.weightSum = 1f
            val newTab = ATextView(context)
            newTab.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            newTab.gravity = Gravity.CENTER
            newTab.maxLines = 1
            newTab.text = tab.text

            newTab.setTextColor(ContextCompat.getColor(activity, R.color.colorWhite))

            layout.addView(newTab)

            tab.customView = layout
            tabLayoutFavorite.addTab(tab)
        }

        tabLayoutFavorite.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                fragment = fragments[tab.position]
                viewPagerFavorite.currentItem = tab.position



            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun initViewPager() {
        fragments.add(FavoriteEventFragment.newInstance())
        fragments.add(FavoriteTeamFragment.newInstance())


        fragment = fragments[0]
        this.tabAdapter = LfPagerAdapter(requireFragmentManager(), fragments)

        viewPagerFavorite.adapter = tabAdapter
        viewPagerFavorite.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutFavorite))
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