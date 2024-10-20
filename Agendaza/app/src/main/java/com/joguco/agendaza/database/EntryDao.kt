package com.joguco.agendaza.database

import androidx.room.*
import com.joguco.agendaza.database.entities.EntryEntity

@Dao
interface EntryDao:MyDao {
    @Query("SELECT * FROM entry_entity")
    override fun getAllEntries(): MutableList<EntryEntity>

    @Query("SELECT * FROM entry_entity WHERE id LIKE :id")
    override fun getEntryById(id: Long): EntryEntity

    @Insert
    override fun addEntry(entryEntity : EntryEntity):Long

    @Update
    override fun updateEntry(entryEntity: EntryEntity):Int

    @Delete
    override fun deleteEntry(entryEntity: EntryEntity):Int
}