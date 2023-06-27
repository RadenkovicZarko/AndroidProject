package rs.raf.vezbe11.presentation.view.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.data.models.converters.DateConverter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class PersonalStatsFragment: Fragment(R.layout.fragment_personal_stats) {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()
    /////////////////////////////
    var graphGV: GraphView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view : View) {
        initGraph(view)
    }

    private fun initGraph(view: View){
        graphGV = view.findViewById(R.id.idGraphView)

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf(
                // on below line we are adding
                // each point on our x and y axis.
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 3.0),
                DataPoint(2.0, 4.0),
                DataPoint(3.0, 9.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 3.0),
                DataPoint(6.0, 6.0),
                DataPoint(7.0, 1.0),
                DataPoint(8.0, 2.0)
            )
        )

        // on below line adding animation
        graphGV?.animate()

        // on below line we are setting scrollable
        // for point graph view
        graphGV?.viewport?.isScrollable = true

        // on below line we are setting scalable.
        graphGV?.viewport?.isScalable = true

        // on below line we are setting scalable y
        graphGV?.viewport?.setScalableY(true)

        // on below line we are setting scrollable y
        graphGV?.viewport?.setScrollableY(true)

        // on below line we are setting color for series.
//        series.color = 1

        // on below line we are adding
        // data series to our graph view.
        graphGV?.addSeries(series)
    }


}