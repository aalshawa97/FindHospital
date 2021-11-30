package com.example.findhospital.ui.graph

import com.example.findhospital.ui.graph.Metric.NEGATIVE

import com.example.findhospital.ui.graph.Metric
import android.graphics.RectF
import com.robinhood.spark.SparkAdapter

class CovidSparkAdapter(private val dailyData: List<CovidData>) : SparkAdapter() {

    var daysAgo = TimeScale.MAX
    var metric = com.example.findhospital.ui.graph.Metric.POSITIVE

    override fun getY(index: Int): Float {
        val chosenDayData = dailyData[index]
        return when (metric) {
            NEGATIVE -> chosenDayData.negativeIncrease.toFloat()
            com.example.findhospital.ui.graph.Metric.POSITIVE -> chosenDayData.positiveIncrease.toFloat()
            com.example.findhospital.ui.graph.Metric.DEATH -> chosenDayData.deathIncrease.toFloat()
        }
    }

    override fun getItem(index: Int) = dailyData[index]

    override fun getCount() = dailyData.size

    override fun getDataBounds(): RectF {
        val bounds = super.getDataBounds()
        if (daysAgo != TimeScale.MAX) {
            bounds.left = count - daysAgo.numDays.toFloat()
        }
        return bounds
    }
}
