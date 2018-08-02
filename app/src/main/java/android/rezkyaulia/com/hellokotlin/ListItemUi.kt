import android.rezkyaulia.com.hellokotlin.R
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*

class ListItemUi : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                padding = dip(16)

                imageView {
                    id = Ids.image
                    image = ContextCompat.getDrawable(context, R.drawable.img_chelsea)
                }.lparams(width = dip(50), height = dip(50))
                textView {
                    id = Ids.name
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_VERTICAL
                    margin = dip(10)
                }
            }
        }



    companion object Ids {
        val image = 1
        val name = 2
    }
}