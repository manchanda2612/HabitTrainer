package sapi.com.habittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_create_habit.*
import kotlinx.android.synthetic.main.single_card.*
import sapi.com.habittrainer.database.HabitDbTable
import sapi.com.habittrainer.database.HabitTrainerDb
import java.io.IOException

class CreateHabitActivity : AppCompatActivity() {

    private val TAG = CreateHabitActivity::class.java.simpleName
    private val CHOOSE_IMAGE_FROM_GALLER = 1001
    private var mImgBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun chooseImage(view: View) {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose Image")
        startActivityForResult(chooser, CHOOSE_IMAGE_FROM_GALLER)
        Log.d(TAG, "Image Chooser sent")

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CHOOSE_IMAGE_FROM_GALLER && resultCode == Activity.RESULT_OK && null != data && null != data.data) {
            Log.d(TAG, "Image received")
            val bitmap = getImage(data)

            bitmap?.let {
                mImgBitmap = bitmap
                img_create_habit.setImageBitmap(bitmap)
            }
        }
    }

    private fun getImage(data: Intent): Bitmap? {

        return try{
            MediaStore.Images.Media.getBitmap(contentResolver, data.data)
        } catch (ex : IOException) {
            ex.printStackTrace()
            null
        }

    }

    fun saveData(view: View) {

        if(edt_create_habit_title.checkForBlankShortHand()) {
            displayErrorMessage("Title is blank")
            return
        } else  if(edt_create_habit_description.checkForBlank()) {
            displayErrorMessage("Description is blank")
            return
        } else  if(mImgBitmap == null) {
            displayErrorMessage("Please choose Image")
            return
        }
        txv_create_habit_error.visibility = View.GONE
        val title = edt_create_habit_title.text.toString()
        val description = edt_create_habit_description.text.toString()
        // I am putting double exclamation mark because here I am sure that mImgBitmap is not null and
        // kotlin is not able to smart cast it automatically because it can be null
        val habit = Habit(title, description, mImgBitmap!!)

        val id =  HabitDbTable(this).insertHabit(habit)
        if(id == -1L) {
            Log.d(TAG, "Error while storing data")
        } else {
            Log.d(TAG, "Data store successfully")
            finish()
        }
    }

    // Extension Function
    private fun EditText.checkForBlank() : Boolean {
        return this.text.toString().isBlank()
    }

    // or Short hand form of extension function
    private fun EditText.checkForBlankShortHand() = this.text.toString().isBlank()

    private fun displayErrorMessage(message : String) {
        txv_create_habit_error.text = message
        txv_create_habit_error.visibility = View.VISIBLE
    }

}
