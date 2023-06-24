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
class PersonalPlanFragment: Fragment(R.layout.fragment_personal_save_meal) {

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()
    /////////////////////////////
    var datePickerTV: TextView? = null
    var arrayAdapter: ArrayAdapter<String>? = null
    var autocompleteTV: AutoCompleteTextView? = null
    var mealIV: AppCompatImageView? = null
    var saveBtn: Button? = null
    var nameMealTV: TextView? = null
    /////////////////////////////
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    var currentImageUrl = "https://preview.redd.it/h3qww4vmh3191.jpg?width=960&crop=smart&auto=webp&v=enabled&s=9b5ce1a7e50e30a2bc2c2873e895dcdae2626289"
    /////////////////////////////
    val REQUEST_IMAGE_CAPTURE = 1
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view : View) {
//        TODO remove after testing
        val url = "https://preview.redd.it/h3qww4vmh3191.jpg?width=960&crop=smart&auto=webp&v=enabled&s=9b5ce1a7e50e30a2bc2c2873e895dcdae2626289"
        var meal = MealEntity("sd", "meal_test", "test", "category_test", "area_test", "test", "mealThumb", "test", "test", "test", url, "test", "test")
        mainViewModel.setPersonalMealForSaving(meal)

        initImageView(view)
        initNameMeal(view)
        initDatePicker(view)
        initDropDown(view)
        initSave(view)
    }

    private fun initSave(view : View) {
        saveBtn = view.findViewById<Button>(R.id.save_personal_meal_btn)
        saveBtn?.setOnClickListener{
            // TODO save the personal meal to the db
            val type_of_meal = autocompleteTV?.text.toString()
            if (type_of_meal == context?.getString(R.string.choose_a_meal))
                Toast.makeText(requireContext(), "Please choose a type of meal", Toast.LENGTH_SHORT).show()
            else{
//                type_of_meal
//                day
//                month
//                year
//                currentImageUrl

                Toast.makeText(requireContext(), "Meal saved: $type_of_meal", Toast.LENGTH_SHORT).show()
                var date_str = format_date(day, month, year)

                var mealId = mainViewModel.currentPersonalMealSave.value?.idMeal
                var userId = mainViewModel.currentUser.value?.userName
//                val date1 = java.sql.Date.valueOf(date_str)

                val personalMeal = PersonalMealEntity(1, type_of_meal, date_str, currentImageUrl, mealId, userId)

                mainViewModel.insertPersonalMeal(personalMeal)
            }

        }
    }

    private fun format_date(day: Int, month: Int, year: Int): String {
        var day_str = day.toString()
        var month_str = month.toString()
        if (day < 10)
            day_str = "0$day"
        if (month < 10)
            month_str = "0$month"
        return "$year-$month_str-$day_str"
    }

    /////////////////////////////////////
    // Showing image of the meal
    private fun initImageView(view : View) {
        mealIV = view.findViewById<AppCompatImageView>(R.id.saved_mealIV)
        val url = mainViewModel.currentPersonalMealSave.value?.strImageSource
        Glide.with(requireContext()).load(url).circleCrop().into(mealIV?:return)


        mealIV?.setOnClickListener{
            openCamera()
        }

    }

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
        currentImageUrl = imageFile.absolutePath
        return imageFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // The image was captured successfully
            // You can use the 'currentPhotoPath' to access the path of the saved image

            // Add the image to the device's gallery
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(currentImageUrl)
            val contentUri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            activity?.sendBroadcast(mediaScanIntent)

            // Show a toast with the image path
            Glide.with(requireContext()).load(currentImageUrl).circleCrop().into(mealIV?:return)
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

    private fun initNameMeal(view : View) {
        nameMealTV = view.findViewById(R.id.saved_meal_name)
        val name = mainViewModel.currentPersonalMealSave.value?.strMeal
        nameMealTV?.text = name
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