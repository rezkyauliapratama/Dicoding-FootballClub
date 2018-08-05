package android.rezkyaulia.com.hellokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.rezkyaulia.com.hellokotlin.R.color.colorAccent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity() {



    private lateinit var view : MainActivityUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = MainActivityUi()
        view.setContentView(this)


    }



    class MainActivityUi : AnkoComponent<MainActivity>{

        private lateinit var listTeam: RecyclerView
        private lateinit var progressBar: ProgressBar
        private lateinit var swipeRefresh: SwipeRefreshLayout
        private lateinit var spinner: Spinner

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

