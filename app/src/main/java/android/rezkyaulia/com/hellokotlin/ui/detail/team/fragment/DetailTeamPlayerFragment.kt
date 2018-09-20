package android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.databinding.FragmentDetailTeamPlayerBinding
import android.rezkyaulia.com.hellokotlin.ui.detail.team.DetailTeamPlayerViewModel
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_detail_team_player.*
import kotlinx.android.synthetic.main.fragment_team.*

class DetailTeamPlayerFragment: BaseFragment<FragmentDetailTeamPlayerBinding, DetailTeamPlayerViewModel>(){



    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_team_player
    }

    override fun initViewModel(): DetailTeamPlayerViewModel {
        return ViewModelProviders.of(requireActivity(),viewModelFactory).get(DetailTeamPlayerViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }


    private fun initRv() {
        rvPlayer.layoutManager = LinearLayoutManager(context)
        val adapter = PlayerRvAdapter(this,viewModel) { id: String -> teamClicked(id) }

        rvPlayer.adapter = adapter

    }

    private fun teamClicked(idPlayer: String) {
        viewModel.playerIdLD.value = idPlayer
    }
}