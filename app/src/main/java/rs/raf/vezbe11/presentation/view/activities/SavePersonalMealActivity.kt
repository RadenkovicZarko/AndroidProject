package rs.raf.vezbe11.presentation.view.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.converters.DateConverter
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class SavePersonalMealActivity : AppCompatActivity() {
    private val mainViewModel: MainContract.ViewModel by viewModel<MealViewModel>()
    val CURRENT_MEAL_KEY = "currentMealKey"
    val LOGIN_KEY = "loginKey"
    val EDIT = "edit"

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
    var currentImageUrl =
        "https://preview.redd.it/h3qww4vmh3191.jpg?width=960&crop=smart&auto=webp&v=enabled&s=9b5ce1a7e50e30a2bc2c2873e895dcdae2626289"
    var personalMeal: PersonalMealEntity? = null

    /////////////////////////////
    val REQUEST_IMAGE_CAPTURE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_save_meal)
        init()
    }

    private fun init() {
        load_shared_pref()

        initImageView()
        initNameMeal()
        initDatePicker()
        initDropDown()
        initSave()
    }

    private fun load_shared_pref() {
        loadCurrentUser()
        loadCurrentMeal()
        loadCurrentEdit()
    }

    private fun loadCurrentEdit() {
        val message = intent.getStringExtra(EDIT)

        val gson = Gson()
        personalMeal = gson.fromJson(message, PersonalMealEntity::class.java)
    }

    private fun loadCurrentMeal() {
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val message = sharedPreferences.getString(CURRENT_MEAL_KEY, null) ?: return

        if(message == "") return
        val gson = Gson()
        val meal = gson.fromJson(message, MealEntity::class.java)
        mainViewModel.setPersonalMealForSaving(meal)

    }

    private fun loadCurrentUser() {
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val message = sharedPreferences.getString(LOGIN_KEY, null)

        val gson = Gson()
        val user = gson.fromJson(message, UserEntity::class.java)
        mainViewModel.setCurrentUser(user)
    }

    /////////////////////////////////////
    // Showing image of the meal
    private fun initImageView() {
        mealIV = findViewById(R.id.saved_mealIVa)
        var url = mainViewModel.currentPersonalMealSave.value?.strImageSource

        if(url == null || url == "") url = personalMeal?.strPersonalUrl

        Glide.with(this).load(url).circleCrop().into(mealIV ?: return)


        mealIV?.setOnClickListener {
            openCamera()
        }

    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(this.packageManager) != null) {
            // Create the File where the photo should be saved
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Handle the error
                null
            }
            // Continue only if the File was successfully created
            photoFile?.let {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this.applicationContext,
                    "rs.raf.vezbe11.fileprovider",
                    it
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
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
            this.sendBroadcast(mediaScanIntent)

            // Show a toast with the image path
            Glide.with(this.applicationContext).load(currentImageUrl).circleCrop()
                .into(mealIV ?: return)
        }
    }
    /////////////////////////////////////

    private fun initNameMeal() {
        nameMealTV = findViewById(R.id.saved_meal_namea)
        val name = mainViewModel.currentPersonalMealSave.value?.strMeal
        if(name == null || name == "")
            nameMealTV?.text = personalMeal?.strName
        else
            nameMealTV?.text = name
    }

    /////////////////////////////////////

    private fun initDatePicker() {
        datePickerTV = findViewById(R.id.datepickera)

        val c = Calendar.getInstance()

        if (personalMeal != null) {
            extractVals(personalMeal?.date)
        } else {
            year = c.get(Calendar.YEAR)
            month = (c.get(Calendar.MONTH) + 1)
            day = c.get(Calendar.DAY_OF_MONTH)
        }
        changeDate(day, month, year)


        datePickerTV?.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { view, yearr, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    year = yearr
                    month = (monthOfYear + 1)
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

    private fun extractVals(date: java.sql.Date?) {
        if(date == null){
            year = 0
            month = 0
            day = 0

        }else{
            val str = date.toString()
            val arr = str.split("-")
            year = arr[0].toInt()
            month = arr[1].toInt()
            day = arr[2].toInt()
        }
    }

    private fun changeDate(day: Int, month: Int, year: Int) {
        val strDate = ("$day-$month-$year")
        datePickerTV?.text =
            (strDate)
    }

    /////////////////////////////////////

    private fun initDropDown() {
        val items = listOf("Breakfast", "Lunch", "Dinner", "Snack")

        var type = personalMeal?.strTypeOfMeal
        // get reference to the autocomplete text view
        autocompleteTV = findViewById(R.id.autoCompleteTextViewa)
        if (type != null) {
            autocompleteTV?.setText(type)
        }

        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        arrayAdapter =
            ArrayAdapter(this.applicationContext, R.layout.dropdown_item_meal_type, items)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV?.setAdapter(arrayAdapter)

    }

    /////////////////////////////////////
    private fun initSave() {
        saveBtn = findViewById<Button>(R.id.save_personal_meal_btna)
        saveBtn?.setOnClickListener {
            // TODO save the personal meal to the db
            val type_of_meal = autocompleteTV?.text.toString()
            if (type_of_meal == this.applicationContext.getString(R.string.choose_a_meal))
                Toast.makeText(
                    this.applicationContext,
                    "Please choose a type of meal",
                    Toast.LENGTH_SHORT
                ).show()
            else {

                var date_str = format_date(day, month, year)

                var mealId = mainViewModel.currentPersonalMealSave.value?.idMeal
                var userId = mainViewModel.currentUser.value?.userName
                var name = nameMealTV?.text.toString()

                val personalMeal = PersonalMealEntity(
                    1,
                    name,
                    type_of_meal,
                    toDate(date_str),
                    currentImageUrl,
                    mealId,
                    userId
                )

                mainViewModel.insertPersonalMeal(personalMeal)
                Toast.makeText(this.applicationContext, "Meal saved: $name", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }

        }
    }

    fun toDate(str: String): java.sql.Date {
        var date = LocalDate.parse(str)
        return java.sql.Date(date.year - 1900, date.monthValue - 1, date.dayOfMonth)
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

}