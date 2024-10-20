package com.joguco.agendaza.database

import com.joguco.agendaza.database.entities.EntryEntity

interface MyDao {
    fun getAllEntries(): MutableList<EntryEntity>

    fun getEntryById(id: Long): EntryEntity

    fun addEntry(entryEntity : EntryEntity):Long

    fun updateEntry(entryEntity: EntryEntity):Int //Numero de filas afectadas

    fun deleteEntry(entryEntity: EntryEntity):Int //Numero de filas afectadas
}