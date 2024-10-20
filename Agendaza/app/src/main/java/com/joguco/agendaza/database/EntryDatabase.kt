package com.joguco.agendaza.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joguco.agendaza.database.entities.EntryEntity

@Database(entities = arrayOf(EntryEntity::class), version = 1)
abstract class EntryDatabase : RoomDatabase() {
    abstract fun taskDao(): EntryDao

    companion object{ //Singleton Pattern
        private var instance:EntryDao? = null

        fun getInstance(context: Context):EntryDao{
            return instance ?: Room.databaseBuilder(context, EntryDatabase::class.java, "agendaza-db").build().taskDao().also { instance = it }
        }
    }
}