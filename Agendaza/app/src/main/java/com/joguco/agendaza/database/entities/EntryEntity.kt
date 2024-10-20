package com.joguco.agendaza.database.entities

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.*

@Entity(tableName = "entry_entity")
data class EntryEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String = "",
    var image: ByteArray = ByteArray(0),
    var date: String = Date(System.currentTimeMillis()).toString(),
    var text: String = "",
    var tags: String = "",
    var favorite: Boolean = false,
    var secret: Boolean = false
)

