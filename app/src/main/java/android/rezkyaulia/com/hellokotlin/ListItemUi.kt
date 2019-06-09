import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.rezkyaulia.com.hellokotlin.R
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ListItemUi : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        frameLayout() {
            layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
           cardView() {
               layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                   leftMargin = dip(10)
                   rightMargin = dip(10)
                   topMargin = dip(5)
                   bottomMargin = dip(5)

               }
               background = GradientDrawable().apply {
                   shape = GradientDrawable.RECTANGLE
                   cornerRadius = 8f
                   setStroke(2, Color.GRAY)

               }
               radius = dip(8).toFloat()

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
        }

    }


    companion object Ids {
        val image = 1
        val name = 2
    }
}