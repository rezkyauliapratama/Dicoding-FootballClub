package android.rezkyaulia.com.hellokotlin.ui.main.last_event

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.Util.TimeUtility
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.databinding.FragmentPrevEventBinding
import android.rezkyaulia.com.hellokotlin.ui.main.EventRvAdapter
import android.rezkyaulia.com.hellokotlin.ui.main.MainViewModel
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_prev_event.*
import org.jetbrains.anko.error
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LastEventFragment : BaseFragment<FragmentPrevEventBinding, LastEventViewModel>() {

    @Inject
    lateinit var timeUtility: TimeUtility

    lateinit var mainViewModel : MainViewModel
    lateinit var adapter : EventRvAdapter
    val eventList : MutableList<Event> = mutableListOf()
    var leagueId : String = ""

    companion object {
        fun newInstance (): LastEventFragment {
            val lastEventFragment = LastEventFragment()
            return lastEventFragment
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_prev_event
    }

    override fun initViewModel(): LastEventViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(LastEventViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = EventRvAdapter(eventList,timeUtility = timeUtility) { event: Event -> eventClicked(event) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel =  ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        initObserver()
        initRv()
        initView()
    }

    private fun initView() {
        swipe_prevEvent.setOnRefreshListener {
            error { "onswipe" }
            viewModel.retrieveData(leagueId)
        }
    }

    private fun initRv() {
        rv_prevEvent.layoutManager = LinearLayoutManager(context)

        rv_prevEvent.adapter = adapter

    }


    fun initObserver(){

        mainViewModel.leagueIdLD.observe(this, android.arch.lifecycle.Observer {
            leagueId = it.toString()
            it?.let { it1 -> viewModel.retrieveData(it1) }


        })

        viewModel.eventResponseLD.observe(this, android.arch.lifecycle.Observer {
            eventList.clear()
            if (it != null) {
                eventList.addAll(it.events)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.uiStatusLD.observe(this, android.arch.lifecycle.Observer {
            if (it == UiStatus.HIDE_LOADER){
                swipe_prevEvent.isRefreshing = false
            }else if (it == UiStatus.SHOW_LOADER){
                swipe_prevEvent.isRefreshing = true
                eventList.clear()
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun eventClicked(event: Event){
        mainViewModel.eventLD.value = event
    }
}