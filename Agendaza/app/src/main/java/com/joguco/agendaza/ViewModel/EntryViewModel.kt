package com.joguco.agendaza.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.joguco.agendaza.database.EntryDatabase
import com.joguco.agendaza.database.MyDao
import com.joguco.agendaza.database.entities.EntryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EntryViewModel (application: Application): AndroidViewModel(application) {
    //Contexto
    val context = application

    //Database
    var myDao: MyDao = EntryDatabase.getInstance(context)

    //UserList
    var entryList: MutableList<EntryEntity> = mutableListOf()

    /*
    * Carga la lista
    * @return    MutableList
     */
    fun getAllEntries(): MutableList<EntryEntity>{
        var job = viewModelScope.launch(Dispatchers.IO) {
            entryList = myDao.getAllEntries()
        }
        runBlocking {
            job.join()
        }
        return entryList
    }

    /*
     * Devuelve entry por ID
     * @param    id
     * @return   EntryEntity
      */
    fun getEntryById(id: Long): EntryEntity{
        var entry = EntryEntity()
        viewModelScope.launch(Dispatchers.IO) {
            entry = myDao.getEntryById(id)
        }
        return entry
    }

    /*
     * Añade Entry
      */
    fun add(title: String, image: ByteArray, text: String, tags: String): EntryEntity {
        var id: Long = 0
        try {
            var job = viewModelScope.launch(Dispatchers.IO) {
                val id = myDao.addEntry(EntryEntity(
                    title = title,
                    image = image,
                    text = text,
                    tags = tags
                ))
            }
            runBlocking {
                job.join()
            }
        } catch (e: Exception) {
            Log.e("DatabaseError", "Error al agregar entrada", e)
        }

        return getEntryById(id)
    }

    /*
    * Borra Entry
    * @param    user
     */
    fun delete(entry: EntryEntity){
        viewModelScope.launch(Dispatchers.IO) {
            myDao.deleteEntry(entry)
        }
    }

    /*
    * Actualiza Entry
    * @param    user
    */
    fun update(entry: EntryEntity){
        viewModelScope.launch(Dispatchers.IO) {
            myDao.updateEntry(entry)
        }
    }
}