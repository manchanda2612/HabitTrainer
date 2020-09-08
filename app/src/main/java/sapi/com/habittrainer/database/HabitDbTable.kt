package sapi.com.habittrainer.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import sapi.com.habittrainer.Habit
import java.io.ByteArrayOutputStream

class HabitDbTable(val context: Context) {

    private val TAG = HabitDbTable::class.java.simpleName

    private val dbHelper = HabitTrainerDb(context)

    fun insertHabit(habit: Habit): Long {

        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with(values) {
            put(HabitEntry.TITLE_COL, habit.title)
            put(HabitEntry.DESCRIPTION_COL, habit.description)
            put(HabitEntry.IMAGE_COL, getByteArray(habit.image))
        }

        val id = db.transaction {
            //Here our function insert that we are passing to extension function that has return value long.
            it.insert(HabitEntry.TABLE_NAME, null, values)
        }

        db.close()
        Log.d(TAG, "Habit Store into the database")

        return id
    }

    private fun getByteArray(bitmap: Bitmap): ByteArray {

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()

    }

    /**
     * Here we don't know what could be the return value it might long, string or unit.
     * So we are actually make this extension function a generic one.
     *
     * very important
     * Now we marked our function as inline function.
     * Wherever we called  transaction function this block of code inside here will basically be copied
     * to the call side. So for instance when we call transaction here above then now since this is
     * a inline function you can just imagine the compiler will replace that call with the exact
     * kind of code that we written in this method or function.
     * In place of "function(this)" compiler simply replace the "insert(HabitEntry.TABLE_NAME, null, values)"
     *
     * So without the inline modifier here. It actually go ahead and create a new anonymous object
     * here for the lambda expression here because it needs to create an object for the function.
     * So that's the same as anonymous inter classes in Java
     * For instance, when you create a click listener in java and specially for Java8 you normally
     * create an anonymous in your class in order to override some on click method and that's the same
     * kind of thing that's gonna happen here, which eventually means that for every invocation it's
     * gonna create a new object and in some cases where you called this higher order function
     * many times that' gonna be lot of overhead specially when you're in Android.
     *
     */
    private inline fun <T> SQLiteDatabase.transaction(function: (SQLiteDatabase) -> T) : T {
        beginTransaction()
        val result = try {
            val returnValue = function(this)
            setTransactionSuccessful()
            returnValue
        } finally {
            endTransaction()
        }
        close()
        return result
    }


    fun getAllHabits() : List<Habit> {

        val db = dbHelper.readableDatabase

        val columns = arrayOf("${HabitEntry.ID}, ${HabitEntry.TITLE_COL}, ${HabitEntry.DESCRIPTION_COL}, ${HabitEntry.IMAGE_COL}")

        val order = "${HabitEntry.ID} ASC"

       // val cursor  = db.query(HabitEntry.TABLE_NAME, columns, null, null, null, null, order)

         val cursor = db.getHabits(HabitEntry.TABLE_NAME, columns, order = order)

        return parseHabitFrom(cursor)

    }


    private fun SQLiteDatabase.getHabits(tableName : String, columns : Array<String>, selection : String? = null,
                                             selectionArgs : Array<String>? = null, groupBy : String? = null, having : String?= null, order : String) : Cursor {

        return query(tableName, columns, selection, selectionArgs, groupBy, having, order)

    }

    private fun Cursor.getValue(columnName : String) = getString(getColumnIndex(columnName))

    private fun Cursor.getImage(columnName : String) : Bitmap {
        val image = getBlob(getColumnIndex(HabitEntry.IMAGE_COL))
        return BitmapFactory.decodeByteArray(image, 0 , image.size)
    }


    private fun parseHabitFrom(cursor : Cursor): MutableList<Habit> {
        val habits = mutableListOf<Habit>()
        while (cursor.moveToNext()) {

            val title = cursor.getValue(HabitEntry.TITLE_COL)
            val description = cursor.getValue(HabitEntry.DESCRIPTION_COL)
            val imageBitmap = cursor.getImage(HabitEntry.IMAGE_COL)

            val habit = Habit(title, description, imageBitmap)

            habits.add(habit)

        }
        cursor.close()

        return habits
    }


}