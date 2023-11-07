package com.example.eric_summer2023


import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.eric_summer2023.databinding.ActivityGraphBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils


class Graph : AppCompatActivity() {
    private lateinit var binding : ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding=ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var balancechart = binding.chart


        balancechart.setTouchEnabled(true)
        balancechart.setPinchZoom(true)

        val dateValues = ArrayList<String>()
        dateValues.add("2023-01-01")
        dateValues.add("2023-01-02")
        dateValues.add("2023-01-03")

        val points= ArrayList<Entry>()
        points.add(Entry(0f, 50f))
        points.add(Entry(1f, 100f))
        points.add(Entry(2f, 75f))

        val set1: LineDataSet
        if (balancechart.data != null && balancechart.data.dataSetCount > 0) {
            set1 = balancechart.data.getDataSetByIndex(0) as LineDataSet
            set1.values =points
            balancechart.data.notifyDataChanged()
            balancechart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(points, "Sample Data")
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            set1.color = Color.MAGENTA
            set1.setCircleColor(Color.DKGRAY)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f


            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.DKGRAY
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            balancechart.data=data
            val xAxis = balancechart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(dateValues)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f // Set the interval between each date
            xAxis.isGranularityEnabled = true
            xAxis.labelRotationAngle = -45f
        }
       /* val set2: LineDataSet
        if ( balancechart.data != null && balancechart.data.dataSetCount > 0)
        {
            set2 =  balancechart.data.getDataSetByIndex(0) as LineDataSet
            set2.values = points
            balancechart.data.notifyDataChanged()
            balancechart.notifyDataSetChanged()
        } else {
            set2 = LineDataSet(points, "Sample Data")
            set2.setDrawIcons(false)
            set2.enableDashedLine(10f, 5f, 0f)
            set2.enableDashedHighlightLine(10f, 5f, 0f)
            set2.color = Color.DKGRAY
            set2.setCircleColor(Color.DKGRAY)
            set2.lineWidth = 1f
            set2.circleRadius = 3f
            set2.setDrawCircleHole(false)
            set2.valueTextSize = 9f
            set2.setDrawFilled(true)
            set2.formLineWidth = 1f
            set2.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set2.formSize = 15f
            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.DKGRAY
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set2)
            val data = LineData(dataSets)
            balancechart.data = data
        }*/

    }

}