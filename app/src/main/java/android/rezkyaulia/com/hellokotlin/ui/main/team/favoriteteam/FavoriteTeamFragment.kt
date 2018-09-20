package android.rezkyaulia.com.hellokotlin.ui.main.team.favoriteteam

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteTeam
import android.rezkyaulia.com.hellokotlin.databinding.FragmentFavoriteTeamBinding
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.rezkyaulia.com.hellokotlin.ui.main.MainViewModel
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_favorite_team.*

/**
 * Created by Rezky Aulia Pratama on 20/9/18.
 */
class FavoriteTeamFragment: BaseFragment<FragmentFavoriteTeamBinding, FavoriteTeamViewModel>(){

    private lateinit var adapter : FavoriteTeamRvAdapter

    companion object {
        fun newInstance (): FavoriteTeamFragment {
            return FavoriteTeamFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite_team
    }

    override fun initViewModel(): FavoriteTeamViewModel {
        return ViewModelProviders.of(this,viewModelFactory).get(FavoriteTeamViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =  ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        adapter = FavoriteTeamRvAdapter(this,viewModel) { favoriteTeam: FavoriteTeam -> eventClicked(favoriteTeam) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRv()
        initObserver()

    }

    private fun initView() {
        swipe_favoriteTeam.setOnRefreshListener {
            viewModel.retrieveData()
        }
    }

    private fun initRv() {
        rv_favoriteTeam.layoutManager = LinearLayoutManager(context)

        rv_favoriteTeam.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveData()

    }

    private fun initObserver(){


        viewModel.uiStatusLD.observe(this, android.arch.lifecycle.Observer {
            if (it == UiStatus.HIDE_LOADER){
                swipe_favoriteTeam.isRefreshing = false
            }else if (it == UiStatus.SHOW_LOADER){
                swipe_favoriteTeam.isRefreshing = true
            }
        })
    }

    private fun eventClicked(favoriteTeam: FavoriteTeam) {
        mainViewModel.teamIdLD.value = favoriteTeam.teamId
    }

}