package rs.raf.vezbe11.presentation.view.fragments

import android.annotation.SuppressLint
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
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import rs.raf.vezbe11.R
import com.bumptech.glide.Glide
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// change it later to fragment_personal_plan
class PersonalPlanFragment: Fragment(R.layout.fragment_personal_save_meal) {
    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null
    var datePickerTV: TextView? = null
    var arrayAdapter: ArrayAdapter<String>? = null
    var autocompleteTV: AutoCompleteTextView? = null
    var mealIV: AppCompatImageView? = null
    var saveBtn: Button? = null
    /////////////////////////////
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
//    TODO change this to the image from the db
    var change_with_db_image = "https://preview.redd.it/h3qww4vmh3191.jpg?width=960&crop=smart&auto=webp&v=enabled&s=9b5ce1a7e50e30a2bc2c2873e895dcdae2626289"
    /////////////////////////////
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view : View) {
        initImageView(view)
        initDropDown(view)
        initDatePicker(view)
        initSave(view)
    }

    private fun initSave(view : View) {
        saveBtn = view.findViewById<Button>(R.id.save_personal_meal_btn)
        saveBtn?.setOnClickListener{
            // TODO save the personal meal to the db
            Toast.makeText(requireContext(), "Meal saved", Toast.LENGTH_SHORT).show()
        }
    }

    /////////////////////////////////////
    // Showing image of the meal
    private fun initImageView(view : View) {
        mealIV = view.findViewById<AppCompatImageView>(R.id.saved_mealIV)
        Glide.with(requireContext()).load(change_with_db_image).circleCrop().into(mealIV?:return)


        mealIV?.setOnClickListener{
            openCamera()
        }

    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            // Create the File where the photo should be saved
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Handle the error
                null
            }
            // Continue only if the File was successfully created
            photoFile?.let {
                val photoURI: Uri = FileProvider.getUriForFile(requireContext(), "rs.raf.vezbe11.fileprovider", it)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val imageFile = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
        change_with_db_image = imageFile.absolutePath
        return imageFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // The image was captured successfully
            // You can use the 'currentPhotoPath' to access the path of the saved image

            // Add the image to the device's gallery
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(change_with_db_image)
            val contentUri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            activity?.sendBroadcast(mediaScanIntent)

            // Show a toast with the image path
            Glide.with(requireContext()).load(change_with_db_image).circleCrop().into(mealIV?:return)
        }
    }


    /////////////////////////////////////

    private fun initDatePicker(view : View) {
        datePickerTV = view.findViewById(R.id.datepicker)

        val c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = (c.get(Calendar.MONTH)+1)
        day = c.get(Calendar.DAY_OF_MONTH)
        changeDate(day, month, year)


        datePickerTV?.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, yearr, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    year = yearr
                    month = (monthOfYear+1)
                    day = dayOfMonth

                    changeDate(day, month, year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

    }

    private fun initDropDown(view : View) {
        val items = listOf("Breakfast", "Lunch", "Dinner", "Snack")
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_meal_type, items)
        // get reference to the autocomplete text view
        autocompleteTV = view.findViewById(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV?.setAdapter(arrayAdapter)
    }

    private fun changeDate(day: Int, month: Int, year: Int) {
        val strDate = ("$day-$month-$year")
        datePickerTV?.text =
            (strDate)
    }
}