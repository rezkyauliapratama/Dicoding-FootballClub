package android.rezkyaulia.com.hellokotlin.main

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.R
import android.rezkyaulia.com.hellokotlin.R.array.league
import android.rezkyaulia.com.hellokotlin.base.BaseActivity
import android.rezkyaulia.com.hellokotlin.data.ApiRepository
import android.rezkyaulia.com.hellokotlin.data.Team
import android.view.View
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : BaseActivity<MainPresenter>(),MainView {
    override fun initPresenter(): MainPresenter {
        return MainPresenter(this)
    }


    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var adapter: MainAdapter

    private lateinit var leagueName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(teams)
        rvListTeam.adapter = adapter

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.support_simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        presenter.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stop()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE

    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()    }


}

