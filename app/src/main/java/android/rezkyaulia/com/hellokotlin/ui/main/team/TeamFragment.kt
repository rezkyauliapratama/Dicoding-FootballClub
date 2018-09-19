package android.rezkyaulia.com.hellokotlin.ui.main.team

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.BR
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.base.BaseFragment
import android.rezkyaulia.com.hellokotlin.databinding.FragmentTeamBinding
import android.rezkyaulia.com.hellokotlin.ui.main.MainViewModel
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.error
import org.jetbrains.anko.support.v4.ctx

class TeamFragment : BaseFragment<FragmentTeamBinding,TeamViewModel>(){

    private lateinit var adapter : TeamRvAdapter

    lateinit var league : String

    companion object {
        fun newInstance (): TeamFragment {
            return TeamFragment()
        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_team
    }

    override fun initViewModel(): TeamViewModel {
        return ViewModelProviders.of(this,viewModelFactory).get(TeamViewModel::class.java)
    }

    override fun initBindingVariable(): Int {
        return BR.viewModel
    }

    override fun inject() {
        initActivityComponent()?.inject(this)
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =  ViewModelProviders.of(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRv()

    }

    private fun initView() {
        val spinnerItems = resources.getStringArray(R.array.league)
        val arrLeague = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item, spinnerItems)
        spinnerTeam.adapter = spinnerAdapter

        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                league = arrLeague[position]
                viewModel.retrieveData(league)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        swipe_team.setOnRefreshListener {
            viewModel.retrieveData(league)
        }
    }

    private fun initRv() {
        rv_team.layoutManager = LinearLayoutManager(context)
        adapter = TeamRvAdapter(this,viewModel) { id: String? -> teamClicked(id) }

        rv_team.adapter = adapter

    }

    private fun teamClicked(id: String?) {
            mainViewModel.teamIdLD.value = id
    }
}