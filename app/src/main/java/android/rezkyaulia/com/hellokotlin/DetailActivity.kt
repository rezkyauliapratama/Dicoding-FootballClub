package android.rezkyaulia.com.hellokotlin

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity() {
    lateinit var ankoUi: DetailActivityUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ankoUi = DetailActivityUi(object : DetailActivityUi.ToolbarTemplateLayoutInterface {
            override fun toolbarStyleId(): Int = R.style.ToolbarStyle
        })
        val layout = ankoUi.setContentView(this)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val item: Item = intent.getParcelableExtra("item")

        ankoUi.textName.text = item.name
        ankoUi.textDesc.text = item.desc
        Glide.with(this).load(item.image).into(ankoUi.imageView)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the toorbar's NavigationIcon as up/home button
            android.R.id.home ->
                //NavigationIcon
            {
                Log.e("Detailactivity", "home button")
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    class DetailActivityUi(private val layoutInterface: ToolbarTemplateLayoutInterface) : AnkoComponent<DetailActivity> {
        lateinit var textName: TextView
        lateinit var textDesc: TextView
        lateinit var imageView: ImageView


        override fun createView(ui: AnkoContext<DetailActivity>): View {
            return with(ui) {
                linearLayout {
                    lparams(matchParent, matchParent)
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)
                    gravity = Gravity.CENTER_HORIZONTAL

                    imageView = imageView {
                        id = Ids.image
                        image = ContextCompat.getDrawable(context, R.drawable.img_barca)
                    }.lparams(width = dip(64), height = dip(64))

                    textName = textView {
                        id = Ids.name
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        margin = dip(5)
                    }

                    textDesc = textView {
                        id = Ids.desc
                    }.lparams(width = wrapContent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        margin = dip(10)
                    }
                }
                /* coordinatorLayout {
                     fitsSystemWindows = true

                     appBarLayout {
                         toolbar() {
                             id = R.id.toolbarId
                         }.lparams(width = matchParent, height = matchParent)

                     }.lparams(width = matchParent)

                    .lparams(width = matchParent, height = matchParent) {
                         behavior = AppBarLayout.ScrollingViewBehavior()
                     }

                 }*/


            }
        }

        interface ToolbarTemplateLayoutInterface {
            fun toolbarStyleId(): Int
        }

        private object Ids {
            val image = 1
            val name = 2
            val desc = 3
        }


    }
}