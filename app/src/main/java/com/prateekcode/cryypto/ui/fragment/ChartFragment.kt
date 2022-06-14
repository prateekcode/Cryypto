package com.prateekcode.cryypto.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.databinding.FragmentDayOneChartBinding
import com.prateekcode.cryypto.utils.*
import com.prateekcode.cryypto.viewmodel.CoinDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : Fragment() {

    private val binding: FragmentDayOneChartBinding by lazy {
        FragmentDayOneChartBinding.inflate(layoutInflater)
    }

    private lateinit var mContext: Context
    private lateinit var customMarkerView: CustomMarkerView

    private val detailVm by viewModels<CoinDetailViewModel>()
    private var entriesOneDay = arrayListOf<Entry>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHistoricalData()
        initObservers()
        initView()
    }

    private fun initView() {
        val requiredTime = arguments?.getString(PARAM_REQUIRED_TIME) ?: ""
        customMarkerView = if (requiredTime == "1")
            CustomMarkerView(mContext, true, R.layout.custom_chart_view)
        else
            CustomMarkerView(mContext, false, R.layout.custom_chart_view)
        binding.chart.apply {
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            marker = customMarkerView
        }

    }

    private fun initHistoricalData() {
        showLoadingPb()
        val currency = arguments?.getString(PARAM_REQUIRED_CURRENCY) ?: ""
        val requiredTime = arguments?.getString(PARAM_REQUIRED_TIME) ?: ""
        if (requiredTime == "1")
            detailVm.getCurrencyHistoricalHourlyData(currency)
        else
            detailVm.getCurrencyHistoryDailyData(currency, requiredTime)
    }

    private fun initObservers() {
        detailVm.historicalDailyData.observe(viewLifecycleOwner) {
            showLog("Data is $it")
            it.data?.forEach {
                entriesOneDay.add(Entry(it.time!!.toFloat(), it.close!!.toFloat()))
            }
            val dataSet = LineDataSet(entriesOneDay, "line chart")
            val changeType = arguments?.getBoolean(PARAM_IS_CHANGE_POSITIVE) ?: false
            updateChart(changeType, dataSet)
        }
        detailVm.historicalHourlyData.observe(viewLifecycleOwner) {
            showLog("Data is $it")
            it.data?.forEach {
                entriesOneDay.add(Entry(it.time!!.toFloat(), it.close!!.toFloat()))
            }
            val dataSet = LineDataSet(entriesOneDay, "line chart")
            val changeType = arguments?.getBoolean(PARAM_IS_CHANGE_POSITIVE) ?: false
            updateChart(changeType, dataSet)
        }
    }

    private fun updateChart(isChangePositive: Boolean, lineDataSet: LineDataSet) {
        lineDataSet.apply {
            lineWidth = 2f
            setDrawFilled(true)
            setDrawHighlightIndicators(false)
            setDrawCircleHole(false)
            setDrawCircles(false)
            setDrawValues(false)
            setDrawIcons(false)
            disableDashedLine()
        }
        if (isChangePositive) {
            lineDataSet.fillDrawable =
                ContextCompat.getDrawable(mContext, R.drawable.chart_fade_green)
            lineDataSet.color = ContextCompat.getColor(
                mContext,
                R.color.green
            )
        } else {
            lineDataSet.fillDrawable =
                ContextCompat.getDrawable(mContext, R.drawable.chart_fade_red)
            lineDataSet.color = ContextCompat.getColor(
                mContext,
                R.color.red_light
            )
        }

        val data = LineData(lineDataSet)
        data.setDrawValues(false)
        binding.chart.apply {
            this.data = data
            animateXY(2000, 2000, Easing.EaseInCubic)
            invalidate()
            hideLoadingPb()
        }
    }

    private fun showLoadingPb() {
        binding.apply {
            loadingPb.show()
            chart.hide()
        }
    }

    private fun hideLoadingPb() {
        binding.apply {
            loadingPb.hide()
            chart.show()
        }
    }
}