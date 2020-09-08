package sapi.com.habittrainer.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HabitTrainerDb (val context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val CREATE_HABIT_TABLE = "CREATE TABLE ${HabitEntry.TABLE_NAME} (" +
            "${HabitEntry.ID} INTEGER PRIMARY KEY," +
            "${HabitEntry.TITLE_COL} TEXT," +
            "${HabitEntry.DESCRIPTION_COL} TEXT," +
            "${HabitEntry.IMAGE_COL} BLOB" +
            ")"

    private val DROP_HABIT_TABLE = "DROP TABLE IF EXISTS ${HabitEntry.TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_HABIT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DROP_HABIT_TABLE)
        onCreate(db)
    }


    fun dropTable(db: SQLiteDatabase?) {
        db?.execSQL(DROP_HABIT_TABLE)
    }



}