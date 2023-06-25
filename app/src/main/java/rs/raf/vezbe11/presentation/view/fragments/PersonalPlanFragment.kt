package rs.raf.vezbe11.presentation.view.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import rs.raf.vezbe11.R
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// change it later to fragment_personal_plan
// INSERT INTO users VALUES ("Vanjce","123",21,210,150,0,10)
class PersonalPlanFragment: Fragment(R.layout.fragment_personal_plan) {


}