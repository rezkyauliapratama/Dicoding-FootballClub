package android.rezkyaulia.com.hellokotlin.ui.main.event

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.databinding.FragmentEventBinding
import android.rezkyaulia.com.hellokotlin.ui.main.event.lastevent.LastEventFragment
import android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent.NextEventFragment
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.app.infideap.stylishwidget.view.ATextView
import kotlinx.android.synthetic.main.fragment_event.*
import org.jetbrains.anko.error

/**
 * Created by Rezky Aulia Pratama on 18/9/18.
 */
class EventFragment : BaseFragment<FragmentEventBinding,EventViewModel>(){


    lateinit var fragments: MutableList<Fragment>
    lateinit var fragment: Fragment
    private lateinit var tabAdapter: LfPagerAdapter



    companion object {
        fun newInstance (): EventFragment {
            return EventFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_event
    }

    override fun initViewModel(): EventViewModel {
        return ViewModelProviders.of(this,viewModelFactory).get(EventViewModel::class.java)
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
        val tabs = arrayOf(tabLayoutEvent.newTab().setText("Prev"), tabLayoutEvent.newTab().setText("Next"))

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
            tabLayoutEvent.addTab(tab)
        }

        tabLayoutEvent.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                fragment = fragments[tab.position]
                viewPagerEvent.currentItem = tab.position



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


        fragment = fragments[0]
        this.tabAdapter = LfPagerAdapter(requireFragmentManager(), fragments)

        viewPagerEvent.adapter = tabAdapter
        viewPagerEvent.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutEvent))
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
