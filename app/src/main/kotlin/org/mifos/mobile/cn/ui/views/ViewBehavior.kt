package org.mifos.mobile.cn.ui.views

import android.content.Context
import android.os.Build
import com.google.android.material.appbar.AppBarLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import org.mifos.mobile.cn.R


class ViewBehavior : CoordinatorLayout.Behavior<HeaderView>{
   private lateinit   var context : Context
    private var startMarginLeft: Int = 0
    private var endMarginLeft: Int =0
    private var marginRight: Int = 0
    private var startMarginBottom: Int = 0
    private var isHide: Boolean = false


    constructor (context: Context,attr:AttributeSet){
        this.context = context

    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: HeaderView, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: HeaderView, dependency: View): Boolean {
        shouldInitProperties(child!!, dependency!!)
        val maxScroll:Int = (dependency as AppBarLayout).totalScrollRange
        val percentage:Float = Math.abs(dependency.getY()) / maxScroll.toFloat()

        var childPosition : Float= (dependency.getHeight() + dependency.getY()
                - child.height
                - (getToolbarHeight() - child.height) * percentage / 2)

        childPosition = childPosition - startMarginBottom * (1f - percentage)

        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.leftMargin = (percentage * endMarginLeft).toInt() + startMarginLeft
        lp.rightMargin = marginRight
        child.layoutParams = lp
        child.y = childPosition

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (isHide && percentage < 1) {
                child.visibility = View.VISIBLE
                isHide = false
            } else if (!isHide && percentage == 1f) {
                child.visibility = View.GONE
                isHide = true
            }
        }
                return true
    }

    private fun shouldInitProperties(child: HeaderView, dependency: View) {

        if (startMarginLeft == 0) {
            startMarginLeft = context.resources.getDimensionPixelOffset(
                    R.dimen.header_view_start_margin_left)
        }

        if (endMarginLeft == 0) {
            endMarginLeft = context.resources.getDimensionPixelOffset(
                    R.dimen.header_view_end_margin_left)
        }

        if (startMarginBottom == 0) {
            startMarginBottom = context.resources.getDimensionPixelOffset(
                    R.dimen.header_view_start_margin_bottom)
        }

        if (marginRight == 0) {
            marginRight = context.resources.getDimensionPixelOffset(
                    R.dimen.header_view_end_margin_right)
        }

    }

    fun getToolbarHeight(): Int {
        var result = 0
        val tv = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            result = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.resources.displayMetrics)
        }
        return result
    }


}