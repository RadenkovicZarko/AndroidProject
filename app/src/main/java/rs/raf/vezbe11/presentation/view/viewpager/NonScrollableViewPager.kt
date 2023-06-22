package rs.raf.vezbe11.presentation.view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager

class NonScrollableViewPager: ViewPager {

        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

        override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
            return false
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            return false
        }
}