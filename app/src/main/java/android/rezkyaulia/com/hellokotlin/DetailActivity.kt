package android.rezkyaulia.com.hellokotlin

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.*

class DetailActivity : AppCompatActivity() {
    lateinit var ankoUi:DetailActivityUi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ankoUi = DetailActivityUi()
        ankoUi.setContentView(this)

        val item : Item = intent.getParcelableExtra("item")

        ankoUi.textName.text = item.name
        ankoUi.textDesc.text = item.desc
        Glide.with(this).load(item.image).into(ankoUi.imageView)



    }

    class DetailActivityUi : AnkoComponent<DetailActivity>{
        lateinit var textName : TextView
        lateinit var textDesc : TextView
        lateinit var imageView : ImageView

        override fun createView(ui: AnkoContext<DetailActivity>): View {
            return with(ui){
                linearLayout {
                    lparams(matchParent, matchParent)
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)
                    gravity = Gravity.CENTER_HORIZONTAL

                    imageView = imageView {
                        id = Ids.image
                        image = ContextCompat.getDrawable(context,R.drawable.img_barca)
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

            }
        }
        private object Ids {
            val image = 1
            val name = 2
            val desc = 3
        }


    }
}