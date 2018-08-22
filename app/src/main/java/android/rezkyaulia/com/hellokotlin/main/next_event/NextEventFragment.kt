package android.rezkyaulia.com.hellokotlin.main.next_event

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.databinding.FragmentNextEventBinding
import android.rezkyaulia.com.hellokotlin.main.EventRvAdapter
import android.rezkyaulia.com.hellokotlin.main.MainViewModel
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_next_event.*
import org.jetbrains.anko.error

/**
 * Created by Rezky Aulia Pratama on 22/8/18.
 */
class NextEventFragment : BaseFragment<FragmentNextEventBinding, NextEventViewModel>(){

    lateinit var mainViewModel : MainViewModel
    lateinit var adapter : EventRvAdapter
    val eventList : MutableList<Event> = mutableListOf()
    var leagueId : String = ""

    companion object {
        fun newInstance (): NextEventFragment{
            val lastEventFragment = NextEventFragment()
            return lastEventFragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_next_event
    }

    override fun initViewModel(): NextEventViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(NextEventViewModel::class.java)

    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = EventRvAdapter(eventList) { event: Event -> eventClicked(event) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel =  ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        initObserver()
        initRv()
        initView()
    }

    private fun initView() {
        swipe_nextEvent.setOnRefreshListener {
            error { "onswipe" }
            viewModel.retrieveData(leagueId)
        }
    }

    private fun initRv() {
        rv_nextEvent.layoutManager = LinearLayoutManager(context)

        rv_nextEvent.adapter = adapter

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
                swipe_nextEvent.isRefreshing = false
            }else if (it == UiStatus.SHOW_LOADER){
                swipe_nextEvent.isRefreshing = true
                eventList.clear()
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun eventClicked(event: Event){
        mainViewModel.eventLD.value = event
    }
}