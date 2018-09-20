package android.rezkyaulia.com.hellokotlin.ui.main.event.favoriteevent

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.util.TimeUtility
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.databinding.FragmentFavoriteEventBinding
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.rezkyaulia.com.hellokotlin.ui.main.MainViewModel
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_favorite_event.*
import javax.inject.Inject

/**
 * Created by Rezky Aulia Pratama on 26/8/18.
 */
class FavoriteEventFragment : BaseFragment<FragmentFavoriteEventBinding, FavoriteEventViewModel>(){

    @Inject
    lateinit var timeUtility: TimeUtility

    private lateinit var mainViewModel : MainViewModel
    private lateinit var adapter : FavoriteEventRvAdapter
    private val eventList : MutableList<FavoriteEvent> = mutableListOf()

    companion object {
        fun newInstance (): FavoriteEventFragment {
            return FavoriteEventFragment()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite_event
    }

    override fun initViewModel(): FavoriteEventViewModel {
        return ViewModelProviders.of(this, viewModelFactory).get(FavoriteEventViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FavoriteEventRvAdapter(eventList) { favoriteEvent: FavoriteEvent -> eventClicked(favoriteEvent) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel =  ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        initObserver()
        initRv()
        initView()
    }

    private fun initView() {
        swipe_favoriteEvent.setOnRefreshListener {
            viewModel.retrieveData()
        }
    }

    private fun initRv() {
        rv_favoriteEvent.layoutManager = LinearLayoutManager(context)

        rv_favoriteEvent.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveData()

    }

    private fun initObserver(){


        viewModel.favEventResponseLD.observe(this, android.arch.lifecycle.Observer {
            eventList.clear()
            if (it != null) {
                eventList.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.uiStatusLD.observe(this, android.arch.lifecycle.Observer {
            if (it == UiStatus.HIDE_LOADER){
                swipe_favoriteEvent.isRefreshing = false
            }else if (it == UiStatus.SHOW_LOADER){
                swipe_favoriteEvent.isRefreshing = true
                eventList.clear()
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun eventClicked(favoriteEvent: FavoriteEvent){
        mainViewModel.eventIdLD.value = favoriteEvent.eventId
    }


}