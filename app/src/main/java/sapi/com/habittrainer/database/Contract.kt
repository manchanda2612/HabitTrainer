package sapi.com.habittrainer.database

import android.provider.BaseColumns


val DATABASE_NAME = "HabitTrainer.db"
val DATABASE_VERSION = 10 // Just consider it as 1.0


object HabitEntry : BaseColumns {

    val TABLE_NAME = "habit"
    val ID = "id"
    val TITLE_COL = "title"
    val DESCRIPTION_COL = "description"
    val IMAGE_COL = "image"

}

