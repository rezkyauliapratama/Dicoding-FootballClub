package android.rezkyaulia.com.hellokotlin

import android.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.R.array.league
import android.rezkyaulia.com.hellokotlin.R.color.colorAccent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(), MainView {



    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    private lateinit var view : MainActivityUi
    private lateinit var spinner: Spinner
    private lateinit var leagueName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = MainActivityUi()
        view.setContentView(this)

        adapter = MainAdapter(teams)
        view.listTeam.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        spinner = view.spinner
        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx, R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }


    override fun showLoading() {
        view.progressBar.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        view.progressBar.visibility = View.INVISIBLE

    }

    override fun showTeamList(data: List<Team>) {
        view.swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()    }

    class MainActivityUi : AnkoComponent<MainActivity>{

        lateinit var listTeam: RecyclerView
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var spinner: Spinner

        override fun createView(ui: AnkoContext<MainActivity>): View {
            return with(ui){
                linearLayout {
                    lparams (width = matchParent, height = wrapContent)
                    orientation = LinearLayout.VERTICAL
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    rightPadding = dip(16)

                    spinner = spinner ()
                    swipeRefresh = swipeRefreshLayout {
                        setColorSchemeResources(colorAccent,
                                android.R.color.holo_green_light,
                                android.R.color.holo_orange_light,
                                android.R.color.holo_red_light)

                        relativeLayout{
                            lparams (width = matchParent, height = wrapContent)

                            listTeam = recyclerView {
                                lparams (width = matchParent, height = wrapContent)
                                layoutManager = LinearLayoutManager(ctx)
                            }

                            progressBar = progressBar {
                            }.lparams{
                                centerHorizontally()
                            }
                        }
                    }
                }
            }
        }
        private object Ids {
            val club_list = 1
        }


    }
}

