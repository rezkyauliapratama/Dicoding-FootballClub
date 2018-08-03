package android.rezkyaulia.com.hellokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {

    private var items: MutableList<Item> = mutableListOf()
    private lateinit var view : MainActivityUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = MainActivityUi()
        view.setContentView(this)

        initData()

        view.rv.adapter = RecyclerViewAdapter(this, items) {
            startActivity<DetailActivity>("item" to it)

        }
    }

    private fun initData() {
        val name = resources.getStringArray(R.array.club_name)
        val desc = resources.getStringArray(R.array.club_description)
        val image = resources.obtainTypedArray(R.array.club_image)

        items.clear()

        for (i in name.indices) {
            items.add(Item(name[i],
                    image.getResourceId(i, 0),
                    desc[i]))
        }

        image.recycle()
    }

    class MainActivityUi : AnkoComponent<MainActivity>{

        lateinit var rv: RecyclerView

        override fun createView(ui: AnkoContext<MainActivity>): View {
            return with(ui){
                frameLayout {

                    rv = recyclerView{
                        id = Ids.club_list
                        layoutManager = LinearLayoutManager(context)

                    }.lparams(width = matchParent, height = matchParent)
                }
            }
        }
        private object Ids {
            val club_list = 1
        }


    }
}

