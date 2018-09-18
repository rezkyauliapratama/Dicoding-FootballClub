package android.rezkyaulia.com.hellokotlin.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent

class AViewPager(context: Context, attrs: AttributeSet) : android.support.v4.view.ViewPager(context, attrs) {
    var isPagingEnabled: Boolean = false

    init {
        this.isPagingEnabled = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) super.onTouchEvent(event) else false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isPagingEnabled) super.onInterceptTouchEvent(event) else false
    }

}
