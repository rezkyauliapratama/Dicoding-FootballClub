package android.rezkyaulia.com.hellokotlin.ui.main.nextevent

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.Util.TimeUtility
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.databinding.FragmentNextEventBinding
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.rezkyaulia.com.hellokotlin.ui.main.EventRvAdapter
import android.rezkyaulia.com.hellokotlin.ui.main.MainViewModel
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_next_event.*
import org.jetbrains.anko.error
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 22/8/18.
 */
class NextEventFragment : BaseFragment<FragmentNextEventBinding, NextEventViewModel>(){

    @Inject
    lateinit var timeUtility: TimeUtility

    private lateinit var mainViewModel : MainViewModel
    private lateinit var adapter : EventRvAdapter
    private val eventList : MutableList<Event> = mutableListOf()
    private var leagueId : String = ""

    companion object {
        fun newInstance (): NextEventFragment {
            return NextEventFragment()
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

        adapter = EventRvAdapter(eventList,timeUtility) { id: String -> eventClicked(id) }
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
            viewModel.retrieveData(leagueId)
        }
    }

    private fun initRv() {
        rv_nextEvent.layoutManager = LinearLayoutManager(context)

        rv_nextEvent.adapter = adapter

    }


    private fun initObserver(){

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
            when (it) {
                UiStatus.HIDE_LOADER -> swipe_nextEvent.isRefreshing = false
                UiStatus.SHOW_LOADER -> {swipe_nextEvent.isRefreshing = true
                    eventList.clear()
                    adapter.notifyDataSetChanged()}

                else -> {
                    error { "cannot found uiStatus" }
                }
            }

        })
    }

    private fun eventClicked(id: String){
        mainViewModel.idLD.value = id
    }
}