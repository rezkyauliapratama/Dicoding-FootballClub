package android.rezkyaulia.com.hellokotlin.customview

import android.app.Application
import android.content.Context
import android.rezkyaulia.com.hellokotlin.R
import android.util.AttributeSet
import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject


class UrlImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ImageView(context, attrs, defStyle) {

    var url: String? = null
        set(url) {
            field = url
            generateImage(field)

        }

    private var defaultImageId : Int = R.drawable.ic_picture

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.UrlImageView)
        url = attributes.getString(R.styleable.UrlImageView_url)
        generateImage(url)
        attributes.recycle()
    }

    private fun generateImage(url : String?){

        Picasso.get().
                load(url)?.
                placeholder(defaultImageId)?.
                resize(1024,0)?.
                onlyScaleDown()?.
                centerInside()?.
                into(this)

    }


}