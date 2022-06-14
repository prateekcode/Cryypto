package com.prateekcode.cryypto.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.prateekcode.cryypto.R

@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context, private val isHourly: Boolean, layoutResource: Int) :
    MarkerView(context, layoutResource) {

    private var mOffset: MPPointF? = null

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        val date = findViewById<TextView>(R.id.tvMarkerDate)
        if (isHourly)
            findViewById<TextView>(R.id.tvMarkerTime).show()
        else
            findViewById<TextView>(R.id.tvMarkerTime).hide()
        val time = findViewById<TextView>(R.id.tvMarkerTime)
        val value = findViewById<TextView>(R.id.tvMarkerValue)

        value.text = "₹ ${e!!.y.toString()}"
        date.text = DateConverter.getDateWithMonthAndYear(e.x.toLong())
        time.text = DateConverter.getTime(e.x.toLong())
    }

}