package android.rezkyaulia.com.hellokotlin.ui.detail.team.fragment

import android.arch.lifecycle.ViewModelProviders
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.databinding.FragmentDetailTeamBinding
import android.rezkyaulia.com.hellokotlin.ui.detail.team.DetailTeamPlayerViewModel

class DetailTeamFragment : BaseFragment<FragmentDetailTeamBinding, DetailTeamPlayerViewModel>(){

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail_team
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


}